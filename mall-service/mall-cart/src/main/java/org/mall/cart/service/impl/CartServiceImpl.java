package org.mall.cart.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.mall.cart.dto.CartDTO;
import org.mall.cart.dto.CartItemDTO;
import org.mall.cart.dto.UserInfoDTO;
import org.mall.cart.interceptor.CartInterceptor;
import org.mall.cart.service.CartService;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.Assert;
import org.mall.common.util.ErrorCodeUtil;
import org.mall.product.entity.SkuInfo;
import org.mall.product.feign.IProductClient;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author sxs
 * @since 2023/2/17
 */
@Service
public class CartServiceImpl implements CartService {

    public static final String CART_KEY = "cart:";
    private final StringRedisTemplate redisTemplate;
    private final IProductClient productClient;
    private final ThreadPoolExecutor executor;
    private final ObjectMapper objectMapper;

    public CartServiceImpl(StringRedisTemplate redisTemplate, IProductClient productClient, ThreadPoolExecutor executor, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.productClient = productClient;
        this.executor = executor;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addCartItem(Long skuId, Integer num) throws InterruptedException, JsonProcessingException, ExecutionException {
        String json = getOps().get(skuId.toString());
        if (StringUtils.isNotBlank(json)) {
            CartItemDTO cartItem = objectMapper.readValue(json, CartItemDTO.class);
            cartItem.setCount(num + cartItem.getCount());
            getOps().put(skuId.toString(), objectMapper.writeValueAsString(cartItem));
        } else {
            CartItemDTO newCartItem = new CartItemDTO();
            CompletableFuture<Void> async1 = CompletableFuture.runAsync(() -> {
                //TODO feign
                ResponseEntity<SkuInfo> skuInfoResp = productClient.getSkuInfo(skuId);
                if (skuInfoResp.getCode().equals(ErrorCode.OK.getCode())) {
                    SkuInfo skuInfo = skuInfoResp.getData();
                    Assert.notNull(skuInfo, "");
                    newCartItem.setSkuId(skuInfo.getSkuId());
                    newCartItem.setCheck(true);
                    newCartItem.setTitle(skuInfo.getSkuTitle());
                    newCartItem.setImage(skuInfo.getSkuDefaultImg());
                    newCartItem.setPrice(skuInfo.getPrice());
                    newCartItem.setCount(num);
                } else {
                    throw new BusinessException(ErrorCodeUtil.getErrorCode(skuInfoResp.getCode()));
                }
            }, executor);
            CompletableFuture<Void> async2 = CompletableFuture.runAsync(() -> {
                //TODO feign
                ResponseEntity<List<String>> attrResp = productClient.getSaleAttrString(skuId);
                if (attrResp.getCode().equals(ErrorCode.OK.getCode())) {
                    Assert.notEmpty(attrResp.getData(), "");
                    newCartItem.setSkuAttrs(attrResp.getData());
                } else {
                    throw new BusinessException(ErrorCodeUtil.getErrorCode(attrResp.getCode()));
                }
            }, executor);
            CompletableFuture.allOf(async1, async2).get();
            getOps().put(skuId.toString(), objectMapper.writeValueAsString(newCartItem));
        }

    }

    @Override
    public CartItemDTO getCartItem(Long skuId) throws JsonProcessingException {
        String str = getOps().get(skuId.toString());
        CartItemDTO cartItemDTO = new CartItemDTO();
        if (StringUtils.isNotBlank(str)) {
            cartItemDTO = objectMapper.readValue(str, CartItemDTO.class);
        }
        return cartItemDTO;
    }

    @Override
    public CartDTO getCart() throws JsonProcessingException, ExecutionException, InterruptedException {
        CartDTO cartDTO = new CartDTO();
        UserInfoDTO userInfo = CartInterceptor.threadLocal.get();
        List<CartItemDTO> cartItems = getCartItems(CART_KEY + userInfo.getUserKey());
        if (userInfo.getUserId() != null) {
            //是用户，合并
            for (CartItemDTO cartItemDTO : cartItems) {
                addCartItem(cartItemDTO.getSkuId(), cartItemDTO.getCount());
            }
            //获取用户购物车
            cartItems = getCartItems(CART_KEY + userInfo.getUserId());
            //删除游客购物车
            redisTemplate.delete(CART_KEY + userInfo.getUserKey());
        }
        cartDTO.setItems(cartItems);
        cartDTO.setReduce(new BigDecimal(0));
        return cartDTO;
    }

    @Override
    public void checkItem(Long skuId, Byte checked) throws JsonProcessingException {
        CartItemDTO cartItem = getCartItem(skuId);
        Optional<CartItemDTO> itemOptional = Optional.ofNullable(getCartItem(skuId));
        if (itemOptional.isPresent()) {
            itemOptional.get().setCheck(checked == 1);
            getOps().put(skuId.toString(), objectMapper.writeValueAsString(itemOptional.get()));
        }
    }

    @Override
    public void countItem(Long skuId, Integer num) throws JsonProcessingException {
        CartItemDTO cartItem = getCartItem(skuId);
        Optional<CartItemDTO> itemOptional = Optional.ofNullable(getCartItem(skuId));
        if (itemOptional.isPresent()) {
            itemOptional.get().setCount(num);
            getOps().put(skuId.toString(), objectMapper.writeValueAsString(itemOptional.get()));
        }
    }

    @Override
    public void deleteItem(Long skuId) {
        getOps().delete(skuId.toString());
    }

    @Override
    public List<CartItemDTO> getCheckedCartItems() {
        UserInfoDTO userInfoDTO = CartInterceptor.threadLocal.get();
        if (userInfoDTO.getUserId() == null) {
            return Collections.<CartItemDTO>emptyList();
        }
        List<CartItemDTO> cartItems = getCartItems(CART_KEY + userInfoDTO.getUserId());
        if (CollectionUtils.isEmpty(cartItems)) {
            return Collections.<CartItemDTO>emptyList();
        }
        cartItems = cartItems.stream()
                .filter(CartItemDTO::getCheck)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartItems)) {
            return Collections.<CartItemDTO>emptyList();
        }
        List<Long> skuIds = cartItems.stream()
                .map(CartItemDTO::getSkuId)
                .collect(Collectors.toList());
        //重新查询sku价格
        //TODO feign
        ResponseEntity<Map<Long, BigDecimal>> responseEntity = productClient.getSkuPrices(skuIds);
        if (!responseEntity.isOK()) {
            throw new BusinessException((ErrorCodeUtil.getErrorCode(responseEntity)));
        }
        //TODO 待优化
        Map<Long, BigDecimal> map = responseEntity.getData();
        cartItems.stream().forEach(cartItemDTO -> {
            cartItemDTO.setPrice(map.get(cartItemDTO.getSkuId()));
        });
        return cartItems;
    }

    /**
     * 获取指定key的
     *
     * @return
     */
    private BoundHashOperations<String, String, String> getOps() {
        UserInfoDTO userInfo = CartInterceptor.threadLocal.get();
        if (userInfo.getUserId() != null) {
            return redisTemplate.boundHashOps(CART_KEY + userInfo.getUserId());
        } else {
            return redisTemplate.boundHashOps(CART_KEY + userInfo.getUserKey());
        }
    }

    /**
     * 获取指定key购物车项列表
     *
     * @param key true:是用户 false:
     * @return
     */

    private List<CartItemDTO> getCartItems(String key) {
        List<Object> jsons = redisTemplate.opsForHash().values(key);
        return jsons.stream().map(json -> {
            try {
                return objectMapper.readValue((String) json, CartItemDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
