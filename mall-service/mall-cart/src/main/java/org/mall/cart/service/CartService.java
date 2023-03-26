package org.mall.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mall.cart.dto.CartDTO;
import org.mall.cart.dto.CartItemDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author sxs
 * @since 2023/2/17
 */

public interface CartService {
    /**
     * 添加商品到购物车
     * @param skuId
     * @param num
     */
    void addCartItem(Long skuId, Integer num) throws ExecutionException, InterruptedException, JsonProcessingException;

    /**
     * 获取指定skuid的CartItemDTO
     * @param skuId
     * @return
     */
    CartItemDTO getCartItem(Long skuId) throws JsonProcessingException;

    /**
     * 获取用户的购物车
     * @return
     */
    CartDTO getCart() throws JsonProcessingException, ExecutionException, InterruptedException;

    void checkItem(Long skuId, Byte checked) throws JsonProcessingException;

    void countItem(Long skuId, Integer num) throws JsonProcessingException;

    void deleteItem(Long skuId);

    /**
     * 获取当前用户购物车选中的购物项
     * @return
     */
    List<CartItemDTO> getCheckedCartItems();
}
