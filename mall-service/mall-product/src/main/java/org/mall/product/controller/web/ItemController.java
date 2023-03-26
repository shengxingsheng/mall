package org.mall.product.controller.web;

import org.mall.product.service.SkuInfoService;
import org.mall.product.vo.SkuItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

/**
 * @author sxs
 * @since 2023/2/10
 */
@Controller
public class ItemController {
    private final SkuInfoService skuInfoService;

    public ItemController(SkuInfoService skuInfoService) {
        this.skuInfoService = skuInfoService;
    }

    @GetMapping("/{skuId}.html")
    public String item(@PathVariable Long skuId, Model model) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = skuInfoService.item(skuId);
        model.addAttribute("item", skuItemVo);
        return "item";
    }
}
