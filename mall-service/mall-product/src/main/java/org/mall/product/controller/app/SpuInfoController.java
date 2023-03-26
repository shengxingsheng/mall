package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.product.dto.SpuInfoDTO;
import org.mall.product.query.SpuPageQuery;
import org.mall.product.service.SpuInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * spu信息 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/product/spuinfo")
@Tag(name = "SpuInfoController",description = "spu信息管理")
public class SpuInfoController {

    private final SpuInfoService spuInfoService;

    public SpuInfoController(SpuInfoService spuInfoService) {
        this.spuInfoService = spuInfoService;
    }
    @Operation(description = "分页查询spu信息")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(SpuPageQuery spuPageQuery) {
        return ResponseEntity.ok(spuInfoService.page(spuPageQuery));
    }
    @Operation(description = "新增商品信息")
    @PostMapping
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody SpuInfoDTO spuInfoDTO) {
        spuInfoService.saveSpuAndSku(spuInfoDTO);
        return ResponseEntity.ok();
    }

    @Operation(description = "商品上架")
    @PostMapping("/{id}/up")
    public ResponseEntity<Void> save(@PathVariable Long id) {
        spuInfoService.up(id);
        return ResponseEntity.ok();
    }
}
