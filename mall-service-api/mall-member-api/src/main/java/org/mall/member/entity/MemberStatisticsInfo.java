package org.mall.member.entity;

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
 * 会员统计信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Getter
@Setter
@ToString
@TableName("ums_member_statistics_info")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "MemberStatisticsInfo", description = "$!{table.comment}")
public class MemberStatisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "会员id")
    private Long memberId;

    @Schema(description = "累计消费金额")
    private BigDecimal consumeAmount;

    @Schema(description = "累计优惠金额")
    private BigDecimal couponAmount;

    @Schema(description = "订单数量")
    private Integer orderCount;

    @Schema(description = "优惠券数量")
    private Integer couponCount;

    @Schema(description = "评价数")
    private Integer commentCount;

    @Schema(description = "退货数量")
    private Integer returnOrderCount;

    @Schema(description = "登录次数")
    private Integer loginCount;

    @Schema(description = "关注数量")
    private Integer attendCount;

    @Schema(description = "粉丝数量")
    private Integer fansCount;

    @Schema(description = "收藏的商品数量")
    private Integer collectProductCount;

    @Schema(description = "收藏的专题活动数量")
    private Integer collectSubjectCount;

    @Schema(description = "收藏的评论数量")
    private Integer collectCommentCount;

    @Schema(description = "邀请的朋友数量")
    private Integer inviteFriendCount;

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
