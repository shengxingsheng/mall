package org.mall.ware.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.pojo.PageQuery;

/**
 * @author sxs
 * @since 2023/1/25
 */
@Getter
@Setter
@ToString
@Schema(name = "WareSkuPageQuery",description = "wareSku分页查询参数")
public class WareSkuPageQuery extends PageQuery {
    @Schema(description = "skuId")
    private Long skuId;
    @Schema(description = "skuId")
    private Long wareId;

}
