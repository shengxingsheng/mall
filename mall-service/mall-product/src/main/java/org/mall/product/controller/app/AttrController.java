package org.mall.product.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.product.dto.AttrDTO;
import org.mall.product.entity.ProductAttrValue;
import org.mall.product.service.AttrService;
import org.mall.product.service.ProductAttrValueService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Tag(name = "属性管理", description = "AttrController")
@RestController
@RequestMapping("/product/attr")
public class AttrController {

    private final AttrService attrService;
    private final ProductAttrValueService productAttrValueService;

    public AttrController(AttrService attrService, ProductAttrValueService productAttrValueService) {
        this.attrService = attrService;
        this.productAttrValueService = productAttrValueService;
    }

    @Operation(summary = "获取指定的catelogId的规格参数")
    @GetMapping("/{type}/list/{catelogId}")
    public ResponseEntity<PageResult> pageBaseAttr(@Validated PageQuery pageQuery, @PathVariable Long catelogId, @PathVariable String type) {
        return ResponseEntity.ok(attrService.pageBaseAttr(pageQuery, catelogId, type));
    }

    @Operation(summary = "获取指定attrId的属性信息")
    @GetMapping("/{attrId}")
    public ResponseEntity<AttrDTO> getAttr(@PathVariable Long attrId) {
        return ResponseEntity.ok(attrService.getAttr(attrId));
    }

    @Operation(summary = "新增属性信息和级联的信息")
    @PostMapping()
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody AttrDTO attrDto) {
        attrService.saveAttrAndRelation(attrDto);
        return ResponseEntity.ok();
    }

    @Operation(summary = "更新属性信息和关联")
    @PutMapping()
    public ResponseEntity<Void> update(@Validated(UpdateGroup.class) @RequestBody AttrDTO attrDto) {
        attrService.updateAttrAndRelation(attrDto);
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取指定id的spu的规格")
    @Parameter(name = "spuId", description = "spu的id", required = true, in = ParameterIn.PATH)
    @GetMapping("/base/listforspu/{spuId}")
    public ResponseEntity<List<ProductAttrValue>> listForSpu(@PathVariable(name = "spuId") Long spuId) {
        return ResponseEntity.ok(productAttrValueService.getBySpuId(spuId));
    }

    @Operation(summary = "修改商品sku规格")
    @Parameter(name = "spuId", description = "spu的id", required = true, in = ParameterIn.PATH)
    @PutMapping("/base/{spuId}")
    public ResponseEntity<Void> updateForSpu(@PathVariable(name = "spuId") Long spuId,
                                            @Validated(UpdateGroup.class) @RequestBody List<ProductAttrValue> attrList) {
        productAttrValueService.updateBySpuId(spuId,attrList);
        return ResponseEntity.ok();
    }
}
