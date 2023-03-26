package org.mall.cart.feign;

import org.mall.cart.dto.CartItemDTO;
import org.mall.common.pojo.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/19
 */
@FeignClient("mall-cart")
public interface ICartClient {

    /**
     * 获取用户购物车选中的购物项
     * @return
     */
    @GetMapping("/checkedCartItems")
    ResponseEntity<List<CartItemDTO>> getCheckedCartItems();
}
