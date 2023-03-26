package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.product.query.SkuPageQuery;
import org.mall.product.service.SkuInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/product/skuinfo")
@Tag(name="SkuInfoController" , description = "sku信息管理")
public class SkuInfoController {
    private final SkuInfoService skuInfoService;


    public SkuInfoController(SkuInfoService skuInfoService) {
        this.skuInfoService = skuInfoService;
    }

    @Operation(description = "分页查询sku信息")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(SkuPageQuery skuPageQuery) {
        return ResponseEntity.ok(skuInfoService.page(skuPageQuery));
    }
}
