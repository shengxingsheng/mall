package org.mall.order.entity;

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
 * 订单配置信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_order_setting")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "OrderSetting", description = "$!{table.comment}")
public class OrderSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "秒杀订单超时关闭时间(分)")
    private Integer flashOrderOvertime;

    @Schema(description = "正常订单超时时间(分)")
    private Integer normalOrderOvertime;

    @Schema(description = "发货后自动确认收货时间（天）")
    private Integer confirmOvertime;

    @Schema(description = "自动完成交易时间，不能申请退货（天）")
    private Integer finishOvertime;

    @Schema(description = "订单完成后自动好评时间（天）")
    private Integer commentOvertime;

    @Schema(description = "会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】")
    private Byte memberLevel;

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
