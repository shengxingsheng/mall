package org.mall.common.dto.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "发给mq的实体")
public class SeckillOrderDTO {
    @Schema(description = "活动场次id")
    private Long promotionSessionId;
    @Schema(description = "商品id")
    private Long skuId;
    @Schema(description = "数量")
    private Integer num;
    @Schema(description = "价格")
    private BigDecimal seckillPrice;
    @Schema(description = "用户id")
    private Long memberId;
    @Schema(description = "订单号")
    private String orderSn;
    @Schema(description = "随机码")
    private String randomCode;
}