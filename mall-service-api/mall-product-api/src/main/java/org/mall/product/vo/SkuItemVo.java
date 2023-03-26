package org.mall.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.product.entity.SkuImages;
import org.mall.product.entity.SkuInfo;
import org.mall.product.entity.SpuInfoDesc;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/10
 */
@Setter
@Getter
@ToString
public class SkuItemVo {
    @Schema(description = "sku基本信息")
    private SkuInfo skuInfo;
    @Schema(description = "sku图片")
    private List<SkuImages> images;
    @Schema(description = "spu的销售属性组合")
    private List<SkuItemSaleAttrVo> saleAttrs;
    @Schema(description = "sku介绍")
    private SpuInfoDesc desc;
    @Schema(description = "spu的基本属性")
    private List<SpuItemBaseAttrVo> groupAttrs;
    @Schema(description = "库存状态")
    private Boolean hasStock = true;
    @Schema(description = "秒杀商品的优惠信息")
    private SeckillSkuVo seckillSkuVo;
}
