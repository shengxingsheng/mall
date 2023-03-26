package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_brand")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "Brand", description = "$!{table.comment}")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "品牌id")
    @TableId(value = "brand_id")
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    private Long brandId;

    @Schema(description = "品牌名")
    @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class})
    private String name;

    @Schema(description = "品牌logo地址")
    @URL(message = "logo地址必须是一个合法的url",groups = {AddGroup.class,UpdateGroup.class})
    private String logo;

    @Schema(description = "介绍")
    private String descript;

    @Schema(description = "显示状态[0-不显示；1-显示]")
    @NotNull(message = "显示状态不能为空", groups = {AddGroup.class})
    @ListValue(vals = {0, 1}, message = "显示状态必须是0或1", groups = {AddGroup.class,UpdateGroup.class})
    private Byte showStatus;

    @Schema(description = "检索首字母")
    @NotBlank(message = "检索首字母不能为空", groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]", message = "检索首字母必须是一个字母", groups = {AddGroup.class,UpdateGroup.class})
    private String firstLetter;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空", groups = {AddGroup.class})
    @Min(value = 0,message = "排序必须大于或等于0", groups = {AddGroup.class,UpdateGroup.class})
    private Integer sort;

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
