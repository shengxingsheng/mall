package org.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品库存
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("wms_ware_sku")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "WareSku", description = "$!{table.comment}")
public class WareSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @NotNull(message = "skuId不能为空",groups = {AddGroup.class})
    @Schema(description = "sku_id")
    private Long skuId;

    @NotNull(message = "wareId不能为空",groups = {AddGroup.class})
    @Schema(description = "仓库id")
    private Long wareId;

    @NotNull(message = "库存数不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "库存数不能为负",groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "库存数")
    private Integer stock;

    @Schema(description = "sku_name")
    private String skuName;

    @NotNull(message = "锁定库存不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "锁定库存不能为负",groups = {AddGroup.class, UpdateGroup.class})
    @Schema(description = "锁定库存")
    private Integer stockLocked;

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
