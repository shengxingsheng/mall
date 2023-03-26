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
 * 订单退货申请
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_order_return_apply")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "OrderReturnApply", description = "$!{table.comment}")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "order_id")
    private Long orderId;

    @Schema(description = "退货商品id")
    private Long skuId;

    @Schema(description = "订单编号")
    private String orderSn;

    @Schema(description = "会员用户名")
    private String memberUsername;

    @Schema(description = "退款金额")
    private BigDecimal returnAmount;

    @Schema(description = "退货人姓名")
    private String returnName;

    @Schema(description = "退货人电话")
    private String returnPhone;

    @Schema(description = "申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]")
    private Boolean status;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "商品图片")
    private String skuImg;

    @Schema(description = "商品名称")
    private String skuName;

    @Schema(description = "商品品牌")
    private String skuBrand;

    @Schema(description = "商品销售属性(JSON)")
    private String skuAttrsVals;

    @Schema(description = "退货数量")
    private Integer skuCount;

    @Schema(description = "商品单价")
    private BigDecimal skuPrice;

    @Schema(description = "商品实际支付单价")
    private BigDecimal skuRealPrice;

    @Schema(description = "原因")
    private String reason;

    @Schema(description = "描述")
    private String description述;

    @Schema(description = "凭证图片，以逗号隔开")
    private String descPics;

    @Schema(description = "处理备注")
    private String handleNote;

    @Schema(description = "处理人员")
    private String handleMan;

    @Schema(description = "收货人")
    private String receiveMan;

    @Schema(description = "收货时间")
    private LocalDateTime receiveTime;

    @Schema(description = "收货备注")
    private String receiveNote;

    @Schema(description = "收货电话")
    private String receivePhone;

    @Schema(description = "公司收货地址")
    private String companyAddress;

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
