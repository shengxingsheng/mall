package org.mall.ware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.ware.entity.WareInfo;
import org.mall.ware.service.WareInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库信息 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/ware/wareinfo")
@Tag(name = "WareInfoController", description = "仓库信息管理")
public class WareInfoController {

    private final WareInfoService wareInfoService;

    public WareInfoController(WareInfoService wareInfoService) {
        this.wareInfoService = wareInfoService;
    }

    @Operation(description = "分页查询")
    @GetMapping("/list")
    public ResponseEntity<PageResult> page(@Validated PageQuery pageQuery) {
        return ResponseEntity.ok(wareInfoService.page(pageQuery));
    }
    @Operation(description = "根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<WareInfo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(wareInfoService.getById(id));
    }
    @Operation(description = "新增仓库信息")
    @PostMapping("")
    public ResponseEntity<Void> save(@Validated({AddGroup.class}) @RequestBody WareInfo wareInfo) {
        wareInfoService.save(wareInfo);
        return ResponseEntity.ok();
    }
    @Operation(description = "更新仓库信息")
    @PutMapping("")
    public ResponseEntity<Void> update(@Validated({UpdateGroup.class}) @RequestBody WareInfo wareInfo) {
        wareInfoService.updateById(wareInfo);
        return ResponseEntity.ok();
    }
    @Operation(description = "删除仓库信息")
    @DeleteMapping("")
    public ResponseEntity<Void> update(@RequestBody List<Long> ids) {
        wareInfoService.removeBatchByIds(ids);
        return ResponseEntity.ok();
    }

}
