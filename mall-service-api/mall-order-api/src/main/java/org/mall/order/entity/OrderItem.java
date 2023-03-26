package org.mall.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单项信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("oms_order_item")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "OrderItem", description = "$!{table.comment}")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "order_id")
    private Long orderId;

    @Schema(description = "order_sn")
    private String orderSn;

    @Schema(description = "spu_id")
    private Long spuId;

    @Schema(description = "spu_name")
    private String spuName;

    @Schema(description = "spu_pic")
    private String spuPic;

    @Schema(description = "品牌")
    private String spuBrand;

    @Schema(description = "商品分类id")
    private Long categoryId;

    @Schema(description = "商品sku编号")
    private Long skuId;

    @Schema(description = "商品sku名字")
    private String skuName;

    @Schema(description = "商品sku图片")
    private String skuPic;

    @Schema(description = "商品sku价格")
    private BigDecimal skuPrice;

    @Schema(description = "商品购买的数量")
    private Integer skuQuantity;

    @Schema(description = "商品销售属性组合（JSON）")
    private String skuAttrsVals;

    @Schema(description = "商品促销分解金额")
    private BigDecimal promotionAmount;

    @Schema(description = "优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @Schema(description = "积分优惠分解金额")
    private BigDecimal integrationAmount;

    @Schema(description = "该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    @Schema(description = "赠送积分")
    private Integer giftIntegration;

    @Schema(description = "赠送成长值")
    private Integer giftGrowth;

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
