package org.mall.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.pojo.PageQuery;

/**
 * @author sxs
 * @since 2023/1/25
 */
@Setter
@Getter
@ToString
@Schema(name = "SpuPageQuery" ,description = "spu分页查询参数")
public class SpuPageQuery extends PageQuery {

    @Schema(description = "状态 0新建 1上架 2下架")
    private Byte status;
    @Schema(description = "品牌id")
    private Long brandId;
    @Schema(description = "分类id")
    private Long catelogId;
}
