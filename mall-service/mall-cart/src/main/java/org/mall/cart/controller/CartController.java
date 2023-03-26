package org.mall.cart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.mall.cart.dto.CartDTO;
import org.mall.cart.dto.CartItemDTO;
import org.mall.cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

/**
 * @author sxs
 * @since 2023/2/16
 */
@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("cart.html")
    public String cart(Model model) throws JsonProcessingException, ExecutionException, InterruptedException {
        CartDTO cartDTO = cartService.getCart();
        model.addAttribute("cart", cartDTO);
        return "cartList";
    }

    /**
     * 加入购物车
     *
     * @return
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException, JsonProcessingException {
        cartService.addCartItem(skuId, num);
        redirectAttributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.mall.org/addSuccess";
    }

    /**
     * 加入购物车成功
     *
     * @return
     */
    @GetMapping("/addSuccess")
    public String addSuccess(@RequestParam("skuId") Long skuId, Model model) throws JsonProcessingException {
        CartItemDTO cartItemDTO = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemDTO);
        return "success";
    }

    /**
     * 修改选中状态
     *
     * @return
     */
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("checked") Byte checked) throws JsonProcessingException {
        cartService.checkItem(skuId, checked);
        return "redirect:http://cart.mall.org/cart.html";
    }

    @Operation(summary = "修改数量")
    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num) throws JsonProcessingException {
        cartService.countItem(skuId, num);
        return "redirect:http://cart.mall.org/cart.html";
    }

    @Operation(summary = "删除")
    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) throws JsonProcessingException {
        cartService.deleteItem(skuId);
        return "redirect:http://cart.mall.org/cart.html";
    }

}
