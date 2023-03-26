package org.mall.ware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.ware.dto.MergeDTO;
import org.mall.ware.dto.PurchaseDoneDTO;
import org.mall.ware.entity.Purchase;
import org.mall.ware.query.PurchasePageQuery;
import org.mall.ware.service.PurchaseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 采购信息 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/ware/purchase")
@Tag(name = "PurchaseController", description = "采购管理")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(PurchasePageQuery query) {
        return ResponseEntity.ok(purchaseService.page(query));
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getById(id));
    }

    @Operation(summary = "新增")
    @PostMapping("")
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody Purchase purchase) {
        purchaseService.save(purchase);
        return ResponseEntity.ok();
    }

    @Operation(summary = "更新")
    @PutMapping("")
    public ResponseEntity<Void> updateById(@Validated(UpdateGroup.class) @RequestBody Purchase purchase) {
        purchaseService.updateById(purchase);
        return ResponseEntity.ok();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("")
    public ResponseEntity<Void> deleteByIds(@RequestBody List<Long> ids) {
        purchaseService.removeBatchByIds(ids);
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取没有被领取的单子")
    @GetMapping("/unreceive/list")
    public ResponseEntity<PageResult> listUnreceive() {
        return ResponseEntity.ok(purchaseService.getUnreceivePage(new PageQuery()));
    }

    @Operation(summary = "领取采购单")
    @PostMapping("/received")
    public ResponseEntity<Void> received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);
        return ResponseEntity.ok();
    }

    @Operation(summary = "合并整单")
    @PostMapping("/merge")
    public ResponseEntity<Void> merge(@Validated @RequestBody MergeDTO mergeDTO) {
        purchaseService.merge(mergeDTO);
        return ResponseEntity.ok();
    }

    @Operation(summary = "完成采购")
    @PostMapping("/done")
    public ResponseEntity<Void> done(@Validated @RequestBody PurchaseDoneDTO purchaseDoneDTO) {
        purchaseService.done(purchaseDoneDTO);
        return ResponseEntity.ok();
    }
}
