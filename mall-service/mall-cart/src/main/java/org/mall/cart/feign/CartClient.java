package org.mall.cart.feign;

import org.mall.cart.dto.CartItemDTO;
import org.mall.cart.service.CartService;
import org.mall.common.pojo.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/19
 */
@RestController
public class CartClient implements ICartClient{
    private final CartService cartService;

    public CartClient(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<List<CartItemDTO>> getCheckedCartItems() {
        return ResponseEntity.ok(cartService.getCheckedCartItems());
    }

}
