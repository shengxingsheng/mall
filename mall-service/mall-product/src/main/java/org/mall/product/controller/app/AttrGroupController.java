package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.product.dto.AttrGroupDTO;
import org.mall.product.entity.Attr;
import org.mall.product.entity.AttrAttrgroupRelation;
import org.mall.product.entity.AttrGroup;
import org.mall.product.service.AttrAttrgroupRelationService;
import org.mall.product.service.AttrGroupService;
import org.mall.product.service.AttrService;
import org.mall.product.dto.AttrGroupWithAttrDTO;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Tag(name = "AttrGroupController", description = "属性组管理")
@RestController
@RequestMapping("/product/attrgroup")
public class AttrGroupController {

    private final AttrGroupService attrGroupService;
    private final AttrService attrService;

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    public AttrGroupController(AttrGroupService attrGroupService, AttrService attrService, AttrAttrgroupRelationService attrAttrgroupRelationService) {
        this.attrGroupService = attrGroupService;
        this.attrService = attrService;
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
    }

    @Operation(summary = "获取分页数据")
    @GetMapping("/tree/{catlogId}")
    public ResponseEntity<PageResult> getPage(@Validated PageQuery pageQuery, @PathVariable Long catlogId) {
        return ResponseEntity.ok(attrGroupService.getPage(pageQuery, catlogId));
    }

    @Operation(summary = "添加属性组")
    @PostMapping("")
    public ResponseEntity<Void> saveAttrGroup(@Validated(AddGroup.class) @RequestBody AttrGroup attrGroup) {
        attrGroupService.save(attrGroup);
        return ResponseEntity.ok();
    }

    @Operation(summary = "修改属性组")
    @PutMapping("")
    public ResponseEntity<Void> updateAttrGroup(@Validated(UpdateGroup.class) @RequestBody AttrGroup attrGroup) {
        attrGroupService.updateById(attrGroup);
        return ResponseEntity.ok();
    }
    @Operation(summary = "删除属性组")
    @DeleteMapping("")
    public ResponseEntity<Void> deleteAttrGroup(@RequestBody List<Long> attrGroupIds) {
        attrGroupService.deleteAttrGroup(attrGroupIds);
        return ResponseEntity.ok();
    }
    @Operation(summary = "获取指定id的属性组")
    @GetMapping("/{attrGroupId}")
    public ResponseEntity<AttrGroupDTO> getDtoById(@PathVariable Long attrGroupId) {
        return ResponseEntity.ok(attrGroupService.getDtoById(attrGroupId));
    }

    @Operation(summary = "获取指定分类id的属性组")
    @GetMapping("/list/{catelogId}")
    public ResponseEntity<PageResult> pageByCatelogId(@Validated PageQuery pageQuery, @PathVariable Long catelogId) {
        return ResponseEntity.ok(attrGroupService.pageByCatelogId(pageQuery, catelogId));
    }

    @Operation(summary = "获取该属性组下关联的所有属性")
    @GetMapping("/{attrGroupId}/attr/relation")
    public ResponseEntity<List<Attr>> getAllAttr(@PathVariable Long attrGroupId) {
        return ResponseEntity.ok(attrService.getByAttrGroupId(attrGroupId));
    }

    @Operation(summary = "删除与属性的关联")
    @DeleteMapping("/attr/relation")
    public ResponseEntity<Void> deleteAtrrRelation(@RequestBody List<AttrAttrgroupRelation> relations) {
        attrGroupService.deleteAtrrRelation(relations);
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取没有被同分类下属性组关联的属性")
    @GetMapping("/{attrGroupId}/noattr/relation")
    public ResponseEntity<PageResult> getNotRelationAttr(@Validated PageQuery pageQuery, @PathVariable Long attrGroupId) {
        return ResponseEntity.ok(attrGroupService.getNotRelationAttr(pageQuery,attrGroupId));
    }

    @Operation(summary = "添加属性关联")
    @PostMapping("/attr/relation")
    public ResponseEntity<Void> saveRelation(@RequestBody List<AttrAttrgroupRelation> relations) {
        if (!CollectionUtils.isEmpty(relations)){
            attrAttrgroupRelationService.saveBatch(relations);
        }
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取分类下所有分组&关联属性")
    @GetMapping("/{catalogId}/withattr")
    public ResponseEntity<List<AttrGroupWithAttrDTO>> getAttrGroupWithAttr(@PathVariable Long catalogId) {
        return ResponseEntity.ok(attrGroupService.getAttrGroupWithAttr(catalogId));
    }
}
