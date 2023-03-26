package org.mall.ware.entity;

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
 * 库存工作单
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString

@TableName("wms_ware_order_task_detail")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "WareOrderTaskDetail", description = "$!{table.comment}")
public class WareOrderTaskDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "sku_id")
    private Long skuId;

    @Schema(description = "sku_name")
    private String skuName;

    @Schema(description = "购买个数")
    private Integer skuNum;

    @Schema(description = "工作单id")
    private Long taskId;
    @Schema(description = "仓库id")
    private Long wareId;

    @Schema(description = "锁定状态,1-已锁定 2-已解锁 3-已扣减")
    private Byte lockStatus;

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
