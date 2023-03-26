package org.mall.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_order")
//@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "Order", description = "$!{table.comment}")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "member_id")
    private Long memberId;

    @Schema(description = "订单号")
    private String orderSn;

    @Schema(description = "使用的优惠券")
    private Long couponId;

    @Schema(description = "用户名")
    private String memberUsername;

    @Schema(description = "订单总额")
    private BigDecimal totalAmount;

    @Schema(description = "应付总额")
    private BigDecimal payAmount;

    @Schema(description = "运费金额")
    private BigDecimal freightAmount;

    @Schema(description = "促销优化金额（促销价、满减、阶梯价）")
    private BigDecimal promotionAmount;

    @Schema(description = "积分抵扣金额")
    private BigDecimal integrationAmount;

    @Schema(description = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @Schema(description = "后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;

    @Schema(description = "支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】")
    private Byte payType;

    @Schema(description = "订单来源[0->PC订单；1->app订单]")
    private Byte sourceType;

    @Schema(description = "订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】")
    private Byte status;

    @Schema(description = "物流公司(配送方式)")
    private String deliveryCompany;

    @Schema(description = "物流单号")
    private String deliverySn;

    @Schema(description = "自动确认时间（天）")
    private Integer autoConfirmDay;

    @Schema(description = "可以获得的积分")
    private Integer integration;

    @Schema(description = "可以获得的成长值")
    private Integer growth;

    @Schema(description = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Byte billType;

    @Schema(description = "发票抬头")
    private String billHeader;

    @Schema(description = "发票内容")
    private String billContent;

    @Schema(description = "收票人电话")
    private String billReceiverPhone;

    @Schema(description = "收票人邮箱")
    private String billReceiverEmail;

    @Schema(description = "收货人姓名")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

    @Schema(description = "收货人邮编")
    private String receiverPostCode;

    @Schema(description = "省份/直辖市")
    private String receiverProvince;

    @Schema(description = "城市")
    private String receiverCity;

    @Schema(description = "区")
    private String receiverRegion;

    @Schema(description = "详细地址")
    private String receiverDetailAddress;

    @Schema(description = "订单备注")
    private String note;

    @Schema(description = "确认收货状态[0->未确认；1->已确认]")
    private Byte confirmStatus;

    @Schema(description = "删除状态【0->未删除；1->已删除】")
    private Byte deleteStatus;

    @Schema(description = "下单时使用的积分")
    private Integer useIntegration;

    @Schema(description = "支付时间")
    private LocalDateTime paymentTime;

    @Schema(description = "发货时间")
    private LocalDateTime deliveryTime;

    @Schema(description = "确认收货时间")
    private LocalDateTime receiveTime;

    @Schema(description = "评价时间")
    private LocalDateTime commentTime;

    @Schema(description = "修改时间")
    private LocalDateTime modifyTime;

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
