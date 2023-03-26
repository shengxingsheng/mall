package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.pojo.ResponseEntity;
import org.mall.product.dto.CategoryDTO;
import org.mall.product.entity.Category;
import org.mall.product.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@RestController
@RequestMapping("product/category")
@Tag(name = "CategoryController", description = "分类管理")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "获取三级分类")
    @GetMapping("/tree")
    public ResponseEntity<CategoryDTO> tree() {
        return ResponseEntity.ok(categoryService.listCategoryTree());
    }

    @Operation(summary = "删除分类")
    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody List<Long> catIds) {
        categoryService.removeBatchByIds(catIds);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "添加分类")
    @PostMapping()
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        log.info(category.toString());
        return ResponseEntity.ok();
    }
    @Operation(summary = "修改分类")
    @PutMapping
    public ResponseEntity<Void> updateCategory(@RequestBody Category category) {
        categoryService.updateCategoryAndCascade(category);
        return ResponseEntity.ok();
    }
    @Operation(summary = "查询分类")
    @GetMapping("/{catId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long catId) {
        return ResponseEntity.ok(categoryService.getCategoryById(catId));
    }
    @Operation(summary = "修改分类排序")
    @PutMapping("/sort")
    public ResponseEntity<Void> updateCategorySort(@RequestBody Category[] category) {
        categoryService.updateCategorySort(category);
        return ResponseEntity.ok();
    }
}
