package org.mall.seckill.dto.chache;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.mall.coupon.entity.SeckillSkuRelation;
import org.mall.product.entity.SkuInfo;

/**
 * @author sxs
 * @since 2023/3/7
 */
@Data
public class SeckillSkuInfoDTO extends SeckillSkuRelation {

    @Schema(description = "商品信息")
    private SkuInfo skuInfo;
    @Schema(description = "开始时间")
    private Long startTime;
    @Schema(description = "结束时间")
    private Long endTime;
    @Schema(description = "随机码")
    private String randomCode;

}
