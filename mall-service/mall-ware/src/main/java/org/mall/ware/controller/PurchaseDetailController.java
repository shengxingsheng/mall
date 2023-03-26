package org.mall.ware.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.ware.entity.PurchaseDetail;
import org.mall.ware.query.PurchasePageQuery;
import org.mall.ware.service.PurchaseDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/ware/purchasedetail")
public class PurchaseDetailController {
    private final PurchaseDetailService purchaseDetailService;

    public PurchaseDetailController(PurchaseDetailService purchaseDetailService) {
        this.purchaseDetailService = purchaseDetailService;
    }

    @Operation(description = "分页查询")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(PurchasePageQuery query) {
        return ResponseEntity.ok(purchaseDetailService.page(query));
    }
    @Operation(description = "根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDetail> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseDetailService.getById(id));
    }
    @Operation(description = "新增")
    @PostMapping("")
    public ResponseEntity<Void> saveDetail(@Validated(AddGroup.class) @RequestBody PurchaseDetail purchaseDetail) {
        purchaseDetailService.save(purchaseDetail);
        return ResponseEntity.ok();
    }
    @Operation(description = "更新")
    @PutMapping("")
    public ResponseEntity<Void> updateById(@Validated(UpdateGroup.class) @RequestBody PurchaseDetail purchaseDetail) {
        purchaseDetailService.updateById(purchaseDetail);
        return ResponseEntity.ok();
    }
    @Operation(description = "删除")
    @DeleteMapping("")
    public ResponseEntity<Void> deleteByIds(@RequestBody List<Long> ids) {
        purchaseDetailService.removeBatchByIds(ids);
        return ResponseEntity.ok();
    }
}
