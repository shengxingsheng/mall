package org.mall.order.entity;

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
 * 支付信息表
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_payment_info")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "PaymentInfo", description = "$!{table.comment}")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "订单号（对外业务号）")
    private String orderSn;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "支付宝交易流水号")
    private String alipayTradeNo;

    @Schema(description = "支付总金额")
    private BigDecimal totalAmount;

    @Schema(description = "交易内容")
    private String subject;

    @Schema(description = "支付状态")
    private String paymentStatus;

    @Schema(description = "确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "回调内容")
    private String callbackContent;

    @Schema(description = "回调时间")
    private LocalDateTime callbackTime;

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
