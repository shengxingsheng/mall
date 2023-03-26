package org.mall.member.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员等级
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Getter
@Setter
@ToString
@TableName("ums_member_level")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "MemberLevel", description = "$!{table.comment}")
public class MemberLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @NotBlank(message = "等级名称不能为空",groups = {AddGroup.class})
    @Schema(description = "等级名称")
    private String name;

    @NotNull(message = "等级需要的成长值不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "等级需要的成长值不能为负",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "等级需要的成长值")
    private Integer growthPoint;

    @NotNull(message = "默认等级不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "默认等级是能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "是否为默认等级[0->不是；1->是]")
    private Byte defaultStatus;

    @NotNull(message = "免运费标准不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "免运费标准不能为负",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "免运费标准")
    private BigDecimal freeFreightPoint;

    @NotNull(message = "每次评价获取的成长值不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "每次评价获取的成长值不能为负",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "每次评价获取的成长值")
    private Integer commentGrowthPoint;

    @NotNull(message = "是否有免邮特权不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "是否有免邮特权只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "是否有免邮特权")
    private Byte priviledgeFreeFreight;

    @NotNull(message = "是否有会员价格特权不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "是否有会员价格特权只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "是否有会员价格特权")
    private Byte priviledgeMemberPrice;

    @NotNull(message = "是否有生日特权不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "是否有生日特权只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "是否有生日特权")
    private Byte priviledgeBirthday;

    @NotBlank(message = "备注不能为空",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "备注")
    private String note;

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
