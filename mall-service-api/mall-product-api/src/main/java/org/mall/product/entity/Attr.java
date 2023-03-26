package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_attr")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "Attr", description = "$!{table.comment}")
public class Attr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "属性id")
    @TableId(value = "attr_id")
    @NotNull(message = "属性id不能为空",groups = {UpdateGroup.class})
    private Long attrId;

    @Schema(description = "属性名")
    @NotBlank(message = "属性名不能为空",groups = {AddGroup.class})
    private String attrName;

    @Schema(description = "是否需要检索[0-不需要，1-需要]")
    @NotNull(message = "检索状态不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "检索状态只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    private Byte searchType;

    @Schema(description = "值类型[0-为单个值，1-可以选择多个值]")
    @NotNull(message = "值类型不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "值类型只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    private Byte valueType;

    @Schema(description = "属性图标")
    @NotBlank(message = "属性图标不能为空",groups = {AddGroup.class})
    private String icon;

    @Schema(description = "可选值列表[用逗号分隔]")
    private String valueSelect;

    @Schema(description = "属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]")
    @NotNull(message = "属性类型不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "属性类型只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    private Byte attrType;

    @Schema(description = "启用状态[0 - 禁用，1 - 启用]")
    @NotNull(message = "启用状态不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "启用状态只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    private Byte enable;

    @Schema(description = "所属分类")
    @NotNull(message = "所属分类不能为空",groups = {AddGroup.class})
    private Long catelogId;

    @Schema(description = "快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
    @NotNull(message = "快速展示不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "快速展示只能是0或1",groups = {AddGroup.class,UpdateGroup.class})
    private Byte showDesc;

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
