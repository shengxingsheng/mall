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
 * 商品会员价格
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("sms_member_price")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "MemberPrice", description = "$!{table.comment}")
public class MemberPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "sku_id")
    private Long skuId;

    @Schema(description = "会员等级id")
    private Long memberLevelId;

    @Schema(description = "会员等级名")
    private String memberLevelName;

    @Schema(description = "会员对应价格")
    private BigDecimal memberPrice;

    @Schema(description = "可否叠加其他优惠[0-不可叠加优惠，1-可叠加]")
    private Boolean addOther;

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
