package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * sku图片
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_sku_images")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SkuImages", description = "$!{table.comment}")
public class SkuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "sku_id")
    private Long skuId;

    @URL(message = "url格式错误",groups = {AddGroup.class})
    @Schema(description = "图片地址")
    private String imgUrl;

    @Schema(description = "排序")
    private Integer imgSort;

    @ListValue(vals = {0,1},message = "默认图值只能是0或1",groups = {AddGroup.class})
    @Schema(description = "默认图[0 - 不是默认图，1 - 是默认图]")
    private Byte defaultImg;

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
