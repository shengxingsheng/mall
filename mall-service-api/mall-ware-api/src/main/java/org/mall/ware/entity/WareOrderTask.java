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
@TableName("wms_ware_order_task")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "WareOrderTask", description = "$!{table.comment}")
public class WareOrderTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "order_id")
    private Long orderId;

    @Schema(description = "order_sn")
    private String orderSn;

    @Schema(description = "收货人")
    private String consignee;

    @Schema(description = "收货人电话")
    private String consigneeTel;

    @Schema(description = "配送地址")
    private String deliveryAddress;

    @Schema(description = "订单备注")
    private String orderComment;

    @Schema(description = "付款方式【 1:在线付款 2:货到付款】")
    private Boolean paymentWay;

    @Schema(description = "任务状态")
    private Byte taskStatus;

    @Schema(description = "订单描述")
    private String orderBody;

    @Schema(description = "物流单号")
    private String trackingNo;

    @Schema(description = "仓库id")
    private Long wareId;

    @Schema(description = "工作单备注")
    private String taskComment;

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
