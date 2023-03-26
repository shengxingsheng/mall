package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.product.entity.CategoryBrandRelation;
import org.mall.product.service.CategoryBrandRelationService;
import org.mall.product.dto.BrandDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/product/categorybrandrelation")
@Tag(name = "CategoryBrandRelationController")
public class CategoryBrandRelationController {

    private CategoryBrandRelationService categoryBrandRelationService;

    public CategoryBrandRelationController(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    @Operation(summary = "通过品牌id查询关联")
    @GetMapping("/catelog/list")
    public ResponseEntity<List<CategoryBrandRelation>> list(Long brandId) {
        return ResponseEntity.ok(categoryBrandRelationService.listByBrandId(brandId));
    }

    @Operation(summary = "添加关联")
    @PostMapping("")
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelationService.saveRelation(categoryBrandRelation);
        return ResponseEntity.ok();
    }

    @Operation(summary = "删除关联")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody List<Long> ids) {
        categoryBrandRelationService.removeBatchByIds(ids);
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取指定分类下的品牌名字")
    @GetMapping("/brands/list")
    public ResponseEntity<BrandDTO> getByCatId(Long catId) {
        return ResponseEntity.ok(categoryBrandRelationService.getBrandNameByCatId(catId));
    }
}
