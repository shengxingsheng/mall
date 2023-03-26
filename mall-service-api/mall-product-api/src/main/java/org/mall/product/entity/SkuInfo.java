package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * sku信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_sku_info")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SkuInfo", description = "$!{table.comment}")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "skuId")
    @TableId(value = "sku_id")
    private Long skuId;

    @Schema(description = "spuId")
    private Long spuId;

    @NotBlank(message = "sku名称不能为空",groups = {AddGroup.class})
    @Schema(description = "sku名称")
    private String skuName;

    @Schema(description = "sku介绍描述")
    private String skuDesc;

    @Schema(description = "所属分类id")
    private Long catalogId;

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "默认图片")
    private String skuDefaultImg;

    @Schema(description = "标题")
    private String skuTitle;

    @Schema(description = "副标题")
    private String skuSubtitle;

    @Min(value = 0,message = "价格不能为负",groups = {AddGroup.class})
    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "销量")
    private Long saleCount;

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
