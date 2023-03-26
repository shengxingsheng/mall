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
 * spu信息介绍
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_spu_info_desc")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SpuInfoDesc", description = "$!{table.comment}")
public class SpuInfoDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id")
    @TableId(value = "spu_id")
    private Long spuId;

    @Schema(description = "商品介绍")
    private String decript;

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
