package org.mall.search.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * ?catalog3Id=255&keyword=小米&sort=salecount_asc & hasStock=0/1 &
 * @author sxs
 * @since 2023/2/8
 */
@Getter
@Setter
@ToString
@Schema(name = "SearchQuery")
public class SearchQuery {

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "三级分类id")
    private String catalog3Id;

    @Schema(description = "排序,sort=saleCount_asc/desc")
    private String sort;

    @Schema(description = "只显示有货 1显示有货 0都显示")
    private Integer hasStock;
    @Schema(description = "价格区间")
    private String skuPrice;
    @Schema(description = "品牌id")
    private List<String> brandIds;
    @Schema(description = "属性")
    private List<String> attrs;

    @Schema(description = "页码")
    private Integer pageNum;

    @Schema(description = "所有查询条件")
    private String queryString;

}

















