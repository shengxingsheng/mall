package org.mall.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/29
 */
@Setter
@Getter
@ToString
@Schema(name = "SkuEsDTO",description = "")
public class SkuEsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "skuId不能为空")
    @Schema(description = "skuId")
    private String skuId;
    @Schema(description = "spuId")
    private String spuId;
    @Schema(description = "sku标题")
    private String skuTitle;
    @Schema(description = "sku价格")
    private BigDecimal skuPrice;
    @Schema(description = "sku默认图片")
    private String skuImg;
    @Schema(description = "sku销量")
    private Long saleCount;
    @Schema(description = "sku是否有库存")
    private Boolean hasStock;
    @Schema(description = "sku热度值")
    private Long hotScore;
    @Schema(description = "品牌id")
    private String brandId;
    @Schema(description = "分类id")
    private String catalogId;
    @Schema(description = "品牌名字")
    private String brandName;
    @Schema(description = "品牌logo")
    private String brandImg;
    @Schema(description = "分类名字")
    private String catalogName;

    @Schema(description = "spu属性集合")
    List<SpuAttr> attrs;

    @Setter
    @Getter
    @ToString
    @Schema(name = "SpuAttr",description = "spu属性类")
    public static class SpuAttr{
        @Schema(description = "属性id")
        private String attrId;
        @Schema(description = "属性名字")
        private String attrName;
        @Schema(description = "属性值")
        private String attrValue;
    }
}
