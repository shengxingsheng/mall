package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;
import org.mall.product.entity.SkuImages;
import org.mall.product.entity.SkuInfo;
import org.mall.product.entity.SkuSaleAttrValue;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/24
 */
@Setter
@Getter
@ToString
@Schema(name = "SkuInfoDTO")
public class SkuInfoDTO extends SkuInfo {

    @Valid
    @Schema(description = "销售属性")
    private List<SkuSaleAttrValue> attr;

    @Valid
    @Schema(description = "sku图片")
    private List<SkuImages> images;

    @Schema(description = "属性组合")
    private List<String> descar;


    @Min(value = 0, message = "满减数量不能为负", groups = {AddGroup.class})
    @Schema(description = "折扣满足数量")
    private Integer fullCount;
    @Min(value = 0, message = "折扣不能为负", groups = {AddGroup.class})
    @Max(value = 1, message = "折扣不能大于1", groups = {AddGroup.class})
    @Schema(description = "折扣")
    private BigDecimal discount;
    @ListValue(vals = {0, 1}, message = "可以叠加值只能是0或1", groups = {AddGroup.class})
    @Schema(description = "可以叠加")
    private Byte countStatus;

    @Min(value = 0, message = "满足满减金额不能为负", groups = {AddGroup.class})
    @Schema(description = "满足满减金额")
    private BigDecimal fullPrice;
    @Min(value = 0, message = "满减金额不能为负", groups = {AddGroup.class})
    @Schema(description = "满减")
    private BigDecimal reducePrice;
    @ListValue(vals = {0, 1}, message = "可以叠加值只能是0或1", groups = {AddGroup.class})
    @Schema(description = "可以叠加")
    private Byte priceStatus;

    @Valid
    @Schema(description = "会员价格")
    private List<MemberpriceDTO> memberPrice;


}
