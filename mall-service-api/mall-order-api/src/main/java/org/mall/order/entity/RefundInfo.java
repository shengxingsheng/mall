package org.mall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 退款信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_refund_info")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "RefundInfo", description = "$!{table.comment}")
public class RefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "退款的订单")
    private Long orderReturnId;

    @Schema(description = "退款金额")
    private BigDecimal refund;

    @Schema(description = "退款交易流水号")
    private String refundSn;

    @Schema(description = "退款状态")
    private Boolean refundStatus;

    @Schema(description = "退款渠道[1-支付宝，2-微信，3-银联，4-汇款]")
    private Byte refundChannel;

    private String refundContent;

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
