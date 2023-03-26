package org.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 采购信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("wms_purchase")
@JsonIgnoreProperties({"deleted", "createBy", "updateBy"})
@Schema(name = "Purchase", description = "$!{table.comment}")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "采购单id不能为null",groups = {UpdateGroup.class})
    @Schema(description = "采购单id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "采购人id")
    private Long assigneeId;

    @Schema(description = "采购人名")
    private String assigneeName;

    @Schema(description = "联系方式")
    private String phone;

    @NotNull(message = "优先级不能为null",groups = {AddGroup.class})
    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "仓库id")
    private Long wareId;

    @Schema(description = "总金额")
    private BigDecimal amount;

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
