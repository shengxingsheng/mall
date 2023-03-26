package org.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 秒杀商品通知订阅
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("sms_seckill_sku_notice")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SeckillSkuNotice", description = "$!{table.comment}")
public class SeckillSkuNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "member_id")
    private Long memberId;

    @Schema(description = "sku_id")
    private Long skuId;

    @Schema(description = "活动场次id")
    private Long sessionId;

    @Schema(description = "订阅时间")
    private LocalDateTime subcribeTime;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "通知方式[0-短信，1-邮件]")
    private Boolean noticeType;

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
