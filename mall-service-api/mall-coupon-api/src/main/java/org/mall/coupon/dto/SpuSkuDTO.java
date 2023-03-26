package org.mall.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.coupon.entity.MemberPrice;
import org.mall.coupon.entity.SkuFullReduction;
import org.mall.coupon.entity.SkuLadder;
import org.mall.coupon.entity.SpuBounds;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/25
 */
@Getter
@Setter
@ToString
@Schema(name = "SpuSkuDTO")
public class SpuSkuDTO {
    @Schema(description = "金币积分信息")
    private SpuBounds spuBounds;
    @Schema(description = "满几件打几折")
    private List<SkuLadder> skuLadder;
    @Schema(description = "满几减几")
    private List<SkuFullReduction> skuFullReduction;
    @Schema(description = "会员价格信息")
    private List<MemberPrice> memberPrice;
}
