package org.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀活动商品关联
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("sms_seckill_sku_relation")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SeckillSkuRelation", description = "$!{table.comment}")
public class SeckillSkuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "活动id")
    private Long promotionId;

    @Schema(description = "活动场次id")
    private Long promotionSessionId;

    @Schema(description = "商品id")
    private Long skuId;

    @Schema(description = "秒杀价格")
    private BigDecimal seckillPrice;

    @Schema(description = "秒杀总量")
    private Long seckillCount;

    @Schema(description = "每人限购数量")
    private Long seckillLimit;

    @Schema(description = "排序")
    private Integer seckillSort;

    @Schema(description = "逻辑删除 0未删除 1为删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @Schema(description = "创建人id")
    private Long createBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人id")
    private Long updateBy;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
