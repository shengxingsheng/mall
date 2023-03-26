package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.ListValue;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * spu属性值
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_product_attr_value")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "ProductAttrValue", description = "$!{table.comment}")
public class ProductAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "商品id")
    private Long spuId;

    @NotNull(message = "属性id不能为空",groups = {UpdateGroup.class})
    @Schema(description = "属性id")
    private Long attrId;

    @NotBlank(message = "属性名不能为空",groups = {UpdateGroup.class})
    @Schema(description = "属性名")
    private String attrName;

    @NotBlank(message = "属性值不能为空",groups = {UpdateGroup.class})
    @Schema(description = "属性值")
    private String attrValue;

    @Schema(description = "顺序")
    private Integer attrSort;

    @NotNull(message = "快速展示不能为空",groups = {UpdateGroup.class})
    @ListValue(vals = {0,1},message = "快速展示只能是0或1",groups = {UpdateGroup.class})
    @Schema(description = "快速展示【是否展示在介绍上；0-否 1-是】")
    private Byte quickShow;

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
