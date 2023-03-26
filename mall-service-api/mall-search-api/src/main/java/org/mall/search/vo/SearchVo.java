package org.mall.search.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.search.dto.SkuEsDTO;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/8
 */
@Getter
@Setter
@ToString
@Schema(name = "SearchVo")
public class SearchVo {
    @Schema(description = "商品信息")
    private List<SkuEsDTO> products;

    @Schema(description = "总记录数")
    private Integer totalCount;
    @Schema(description = "当前页码")
    private Integer currPage;
    @Schema(description = "总页码")
    private Integer totalPage;

    @Schema(description = "品牌信息")
    private List<BrandVO> brands;
    @Schema(description = "分类信息")
    private List<CatalogVO> catalogs;
    @Schema(description = "属性信息")
    private List<AttrVO> attrs;

    @Schema(description = "导航页码")
    private List<Integer> pageNavs;
    @Schema(description = "面包屑导航")
    private List<NavVO> navs;
    @Schema(description = "参数携带的")
    private List<String> paramAttrIds;
    @Getter
    @Setter
    @ToString
    public static class NavVO{
        private String link;
        private String navName;
        private String navValue;
    }

    @Getter
    @Setter
    @ToString
    public static class BrandVO{
        @Schema(description = "品牌id")
        private String brandId;
        @Schema(description = "品牌名字")
        private String brandName;
        @Schema(description = "品牌logo")
        private String brandImg;

    }
    @Getter
    @Setter
    @ToString
    public static class CatalogVO{
        @Schema(description = "分类id")
        private String catalogId;
        @Schema(description = "分类名字")
        private String catalogName;
    }
    @Getter
    @Setter
    @ToString
    public static class AttrVO{
        @Schema(description = "属性id")
        private String attrId;
        @Schema(description = "属性名字")
        private String attrName;
        @Schema(description = "属性值")
        private List<String> attrValues;
    }
}
