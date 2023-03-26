package org.mall.product.controller.app;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.product.entity.Brand;
import org.mall.product.service.BrandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/product/brand")
public class BrandController {

    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/list")
    public ResponseEntity<PageResult> page(@Validated PageQuery pageQuery){
        return ResponseEntity.ok(brandService.page(pageQuery));
    }
    @PutMapping("")
    public ResponseEntity<Void> updateBrand(@Validated(UpdateGroup.class)@RequestBody Brand brand){
        brandService.updateBrandAndCascade(brand);
        return ResponseEntity.ok(null);
    }
    @PostMapping("")
    public ResponseEntity<Void> saveBrand(@Validated(AddGroup.class) @RequestBody Brand brand){
        brandService.save(brand);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/{brandId}")
    public ResponseEntity<Void> updateBrand(@PathVariable Long brandId){
        return ResponseEntity.ok(brandService.getById(brandId));
    }
}
