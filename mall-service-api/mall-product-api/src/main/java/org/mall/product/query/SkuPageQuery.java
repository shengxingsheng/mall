package org.mall.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.pojo.PageQuery;

import java.math.BigDecimal;

/**
 * @author sxs
 * @since 2023/1/25
 */
@Setter
@Getter
@ToString
@Schema(name = "SkuPageQuery" ,description = "sku分页查询参数")
public class SkuPageQuery extends PageQuery {
    @Schema(description = "品牌id")
    private Long brandId;
    @Schema(description = "分类id")
    private Long catelogId;
    @Schema(description = "价格范围下限")
    private BigDecimal min;
    @Schema(description = "价格范围上限")
    private BigDecimal max;
}
