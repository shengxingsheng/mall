package org.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;
import org.mall.common.validation.UpdateGroup;
import org.mall.ware.constant.WareConstant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("wms_purchase_detail")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "PurchaseDetail", description = "$!{table.comment}")
public class PurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @TableId(value = "id")
    private Long id;


    @Schema(description = "采购单id")
    private Long purchaseId;

    @NotNull(message = "采购商品id不能为空",groups = {AddGroup.class})
    @Schema(description = "采购商品id")
    private Long skuId;

    @NotNull(message = "采购数量不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "采购数量不能为负",groups = {AddGroup.class,UpdateGroup.class})
    @Schema(description = "采购数量")
    private Integer skuNum;

    @Schema(description = "采购金额")
    private BigDecimal skuPrice;


    @NotNull(message = "仓库id不能为空",groups = {AddGroup.class})
    @Schema(description = "仓库id")
    private Long wareId;

    @NotNull(groups = {AddGroup.class})
    @ListValue(vals = {WareConstant.PURCHASE_STATUS_NEW},message = "状态只能为新建",groups = {AddGroup.class})
    @Schema(description = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    private Byte status;

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
