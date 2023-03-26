package org.mall.coupon.entity;

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
 * 优惠券信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("sms_coupon")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "Coupon", description = "$!{table.comment}")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]")
    private Boolean couponType;

    @Schema(description = "优惠券图片")
    private String couponImg;

    @Schema(description = "优惠卷名字")
    private String couponName;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "每人限领张数")
    private Integer perLimit;

    @Schema(description = "使用门槛")
    private BigDecimal minPoint;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "使用类型[0->全场通用；1->指定分类；2->指定商品]")
    private Boolean useType;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "发行数量")
    private Integer publishCount;

    @Schema(description = "已使用数量")
    private Integer useCount;

    @Schema(description = "领取数量")
    private Integer receiveCount;

    @Schema(description = "可以领取的开始日期")
    private LocalDateTime enableStartTime;

    @Schema(description = "可以领取的结束日期")
    private LocalDateTime enableEndTime;

    @Schema(description = "优惠码")
    private String code;

    @Schema(description = "可以领取的会员等级[0->不限等级，其他-对应等级]")
    private Boolean memberLevel;

    @Schema(description = "发布状态[0-未发布，1-已发布]")
    private Boolean publish;

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
