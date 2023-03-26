package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_category")
@Schema(name = "Category", description = "$!{table.comment}")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类id")
    @TableId(value = "cat_id")
    private Long catId;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父分类id")
    private Long parentCid;

    @Schema(description = "层级")
    private Integer catLevel;

    @Schema(description = "是否显示[0-不显示，1显示]")
    private Byte showStatus;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "图标地址")
    private String icon;

    @Schema(description = "计量单位")
    private String productUnit;

    @Schema(description = "商品数量")
    private Integer productCount;

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
