package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * spu信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_spu_info")
@JsonIgnoreProperties({"deleted", "createBy",  "updateBy"})
@Schema(name = "SpuInfo", description = "$!{table.comment}")
public class SpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id")
    @TableId(value = "id")
    private Long id;

    @NotBlank(message = "商品名称不能为空",groups = {AddGroup.class})
    @Schema(description = "商品名称")
    private String spuName;

    @NotBlank(message = "商品描述不能为空",groups = {AddGroup.class})
    @Schema(description = "商品描述")
    private String spuDescription;

    @NotNull(message = "所属分类id不能为空",groups = {AddGroup.class})
    @Schema(description = "所属分类id")
    private Long catalogId;

    @NotNull(message = "品牌id不能为空",groups = {AddGroup.class})
    @Schema(description = "品牌id")
    private Long brandId;

    @NotNull(message = "商品重量不能为空",groups = {AddGroup.class})
    @Schema(description = "商品重量")
    private BigDecimal weight;

    @NotNull(message = "上架状态不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0,1},message = "上架状态只能为0或1",groups = {AddGroup.class})
    @Schema(description = "上架状态[0 - 下架，1 - 上架]")
    private Byte publishStatus;

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
