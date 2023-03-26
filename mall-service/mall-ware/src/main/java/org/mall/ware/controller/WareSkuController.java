package org.mall.ware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.ware.entity.WareSku;
import org.mall.ware.query.WareSkuPageQuery;
import org.mall.ware.service.WareSkuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品库存 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/ware/waresku")
@Tag(name = "WareSkuController",description = "商品库存管理")
public class WareSkuController {

    private final WareSkuService wareSkuService;

    public WareSkuController(WareSkuService wareSkuService) {
        this.wareSkuService = wareSkuService;
    }

    @Operation(description = "分页查询")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(WareSkuPageQuery wareSkuPageQuery) {
        return ResponseEntity.ok(wareSkuService.page(wareSkuPageQuery));
    }
    @Operation(description = "分页查询")
    @GetMapping("/{id}")
    public ResponseEntity<PageResult> page(@PathVariable Long id) {
        return ResponseEntity.ok(wareSkuService.getById(id));
    }
    @Operation(description = "新增sku库存")
    @PostMapping
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody WareSku wareSku) {
        wareSkuService.saveWareSku(wareSku);
        return ResponseEntity.ok();
    }
    @Operation(description = "更新sku库存")
    @PutMapping
    public ResponseEntity<Void> update(@Validated(UpdateGroup.class) @RequestBody WareSku wareSku) {
        wareSkuService.updateById(wareSku);
        return ResponseEntity.ok();
    }
    @Operation(description = "通过id删除")
    @DeleteMapping
    public ResponseEntity<Void> deleteByIds(@RequestBody List<Long> ids) {
        wareSkuService.removeBatchByIds(ids);
        return ResponseEntity.ok();
    }
}
