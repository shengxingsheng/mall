package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 属性分组
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_attr_group")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "AttrGroup", description = "$!{table.comment}")
public class AttrGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分组id")
    @TableId(value = "attr_group_id")
    @NotNull(message = "分组id不能为空",groups = {UpdateGroup.class})
    private Long attrGroupId;

    @Schema(description = "组名")
    @NotBlank(message = "组名不能为空",groups = {AddGroup.class})
    private String attrGroupName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空", groups = {AddGroup.class})
    @Min(value = 0,message = "排序必须大于或等于0", groups = {AddGroup.class,UpdateGroup.class})
    private Integer sort;

    @Schema(description = "描述")
    @NotBlank(message = "描述不能为空",groups = {AddGroup.class})
    private String descript;

    @Schema(description = "组图标")
//    @NotBlank(message = "组图标地址不能为空",groups = {AddGroup.class})
   // @URL(message = "组图标地址必须是一个合法的url",groups = {AddGroup.class,UpdateGroup.class})
    private String icon;

    @Schema(description = "所属分类id")
    @NotNull(message = "所属分类id不能为空",groups = {AddGroup.class})
    private Long catelogId;

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
