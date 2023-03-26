package org.mall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.mall.search.constant.SearchConstant;
import org.mall.search.dto.SkuEsDTO;
import org.mall.search.query.SearchQuery;
import org.mall.search.service.MallSearchService;
import org.mall.search.vo.SearchVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author sxs
 * @since 2023/2/8
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {

    private final ElasticsearchClient esClient;

    public MallSearchServiceImpl(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    @Override
    public SearchVo search(SearchQuery query) {

        SearchVo searchVo = null;
        //1.构造es请求
        SearchRequest request = buildRequest(query);
        //2.发送
        try {
            SearchResponse<SkuEsDTO> response = esClient.search(request, SkuEsDTO.class);
            searchVo = buildResult(response, query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //3.分析响应数据
        return searchVo;
    }

    /**
     * 构造结果
     *
     * @param response
     * @return
     */
    private SearchVo buildResult(SearchResponse<SkuEsDTO> response, SearchQuery query) {
        SearchVo searchVo = new SearchVo();
        long value = response.hits().total().value();
        searchVo.setTotalCount((int) value);
        searchVo.setTotalPage((int) (value % SearchConstant.PAGE_SIZE == 0 ? value / SearchConstant.PAGE_SIZE : value / SearchConstant.PAGE_SIZE + 1));
        searchVo.setCurrPage(query.getPageNum());
        List<SkuEsDTO> skuEsDTOs = response.hits().hits().stream().map(hit -> {
            SkuEsDTO source = hit.source();
            Map<String, List<String>> map = hit.highlight();
            if (Objects.nonNull(map) && !map.isEmpty()) {
                source.setSkuTitle(map.get("skuTitle").get(0));
            }
            return source;
        }).collect(Collectors.toList());
        searchVo.setProducts(skuEsDTOs);
        //brands
        ArrayList<SearchVo.BrandVO> brandVOs = new ArrayList<>();
        List<StringTermsBucket> brandsBuckets = response.aggregations().get("brands").sterms().buckets().array();
        for (StringTermsBucket brandsBucket : brandsBuckets) {
            SearchVo.BrandVO brandVO = new SearchVo.BrandVO();
            brandVO.setBrandId(brandsBucket.key().stringValue());
            String brandImg = brandsBucket.aggregations().get("brandImg").sterms().buckets().array().get(0).key().stringValue();
            String brandName = brandsBucket.aggregations().get("brandName").sterms().buckets().array().get(0).key().stringValue();
            brandVO.setBrandImg(brandImg);
            brandVO.setBrandName(brandName);
            brandVOs.add(brandVO);
        }
        //catalogs
        ArrayList<SearchVo.CatalogVO> catalogVOs = new ArrayList<>();
        List<StringTermsBucket> catalogs = response.aggregations().get("catalogs").sterms().buckets().array();
        for (StringTermsBucket catalog : catalogs) {
            SearchVo.CatalogVO catalogVO = new SearchVo.CatalogVO();
            catalogVO.setCatalogId(catalog.key().stringValue());
            String catalogName = catalog.aggregations().get("catalogName").sterms().buckets().array().get(0).key().stringValue();
            catalogVO.setCatalogName(catalogName);
            catalogVOs.add(catalogVO);
        }
        //构建attrs
        ArrayList<SearchVo.AttrVO> attrVOs = new ArrayList<>();
        List<StringTermsBucket> attrs = response.aggregations().get("attrs").nested().aggregations().get("attrId").sterms().buckets().array();
        for (StringTermsBucket attr : attrs) {
            SearchVo.AttrVO attrVO = new SearchVo.AttrVO();
            attrVO.setAttrId(attr.key().stringValue());
            List<StringTermsBucket> valueBuckets = attr.aggregations().get("attrValue").sterms().buckets().array();
            List<String> attrValues = valueBuckets.stream().map(attrValue -> attrValue.key().stringValue()).collect(Collectors.toList());
            attrVO.setAttrValues(attrValues);
            String attrName = attr.aggregations().get("attrName").sterms().buckets().array().get(0).key().stringValue();
            attrVO.setAttrName(attrName);
            attrVOs.add(attrVO);
        }
        searchVo.setBrands(brandVOs);
        searchVo.setCatalogs(catalogVOs);
        searchVo.setAttrs(attrVOs);

        //pageNavs
        List<Integer> pageNvs = IntStream.range(1, searchVo.getTotalPage() + 1).mapToObj(Integer::valueOf).collect(Collectors.toList());
        searchVo.setPageNavs(pageNvs);

        searchVo.setParamAttrIds(Collections.EMPTY_LIST);
        //面包屑 属性 navs
        if (!CollectionUtils.isEmpty(query.getAttrs())) {
            Map<String, String> attrMap = searchVo.getAttrs().stream().collect(Collectors.toMap(SearchVo.AttrVO::getAttrId, SearchVo.AttrVO::getAttrName));
            ArrayList<String> attrIds = new ArrayList<>();
            List<SearchVo.NavVO> navVOs = query.getAttrs().stream().map(attr -> {
                SearchVo.NavVO navVO = new SearchVo.NavVO();
                String[] s = attr.split("_");
                navVO.setNavValue(s[1]);
                String attrId = s[0];
                attrIds.add(attrId);
                navVO.setNavName(attrMap.get(attrId));
                navVO.setLink(replaceUrl(query.getQueryString(),"attrs",attrId,""));
                return navVO;
            }).collect(Collectors.toList());
            searchVo.setParamAttrIds(attrIds);
            searchVo.setNavs(navVOs);
        }
        //面包屑导航 navs 品牌
        if (!CollectionUtils.isEmpty(query.getBrandIds())) {
            Map<String, SearchVo.BrandVO> brandMap = searchVo.getBrands().stream().collect(Collectors.toMap(SearchVo.BrandVO::getBrandId, brandVO -> brandVO));
            StringBuffer brandNames = new StringBuffer();
            query.getBrandIds().forEach(brandId -> {
                brandNames.append(brandMap.get(brandId).getBrandName() + ";");
            });
            SearchVo.NavVO navVO = new SearchVo.NavVO();
            navVO.setNavName("品牌");
            navVO.setNavValue(brandNames.toString());
            navVO.setLink(replaceUrl(query.getQueryString(), "brandIds", "",""));
            List<SearchVo.NavVO> navVOs = searchVo.getNavs() == null ? new ArrayList<SearchVo.NavVO>() : searchVo.getNavs();
            navVOs.add(navVO);
            searchVo.setNavs(navVOs);
        }
        //面包屑导航 navs 分类
        if (StringUtils.isNotBlank(query.getCatalog3Id())) {
            Map<String, String> map = searchVo.getCatalogs().stream().collect(Collectors.toMap(SearchVo.CatalogVO::getCatalogId, SearchVo.CatalogVO::getCatalogName));
            SearchVo.NavVO navVO = new SearchVo.NavVO();
            navVO.setNavName("分类");
            navVO.setNavValue(map.get(query.getCatalog3Id()));
            navVO.setLink(replaceUrl(query.getQueryString(), "catalog3Id", "", ""));
            List<SearchVo.NavVO> navVOs = searchVo.getNavs() == null ? new ArrayList<SearchVo.NavVO>() : searchVo.getNavs();
            navVOs.add(navVO);
            searchVo.setNavs(navVOs);
        }
        //paramAttrIds

        return searchVo;
    }

    /**
     * 链接替换
     * @param url
     * @param name
     * @param value
     * @return
     */
    private String replaceUrl(String url,String name,String value,String replaceValue) {
        Pattern pattern = Pattern.compile("([&]?"+name+"="+value+")([^&]*)");
        return pattern.matcher(url).replaceAll(replaceValue);
    }
    /**
     * 构造搜索请求
     *
     * @param query
     * @return
     */
    private SearchRequest buildRequest(SearchQuery query) {
        if (!CollectionUtils.isEmpty(query.getBrandIds())) {
            query.setBrandIds(query.getBrandIds().stream().distinct().collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(query.getAttrs())) {
            query.setAttrs(query.getAttrs().stream().distinct().collect(Collectors.toList()));
        }
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder = builder.index(SearchConstant.INDEX_PRODUCT);
        Query.Builder queryBuilder = new Query.Builder();
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        /**
         * 模糊匹配 属性 分类 品牌 价格区间 库存
         */
        if (StringUtils.isNotBlank(query.getKeyword())) {
            boolQueryBuilder.must(b -> b.match(t -> t.field("skuTitle").query(query.getKeyword())));
        }
        if (StringUtils.isNotBlank(query.getCatalog3Id())) {
            boolQueryBuilder.filter(b -> b.term(f -> f.field("catalogId").value(query.getCatalog3Id())));
        }
        if (!CollectionUtils.isEmpty(query.getBrandIds())) {
            List<FieldValue> values = query.getBrandIds().stream().map(brand -> FieldValue.of(brand)).collect(Collectors.toList());
            boolQueryBuilder.filter(b -> b.terms(t -> t.field("brandId").terms(f -> f.value(values))));
        }
        if (StringUtils.isNotBlank(query.getSkuPrice())) {
            String[] s = query.getSkuPrice().split("_");
            try {
                Long min = 0L;
                Long max = 0L;
                if (StringUtils.isNotBlank(s[1])) {
                    max = Long.valueOf(s[1]);
                }
                if (StringUtils.isNotBlank(s[0])) {
                    min = Long.valueOf(s[0]);
                }
                Long finalMin = min;
                Long finalMax = max;
                if (min > 0 && max > 0 && max >= min) {
                    boolQueryBuilder.filter(f -> f.range(r -> r.field("skuPrice").gte(JsonData.of(finalMin)).lte(JsonData.of(finalMax))));
                } else if (min > 0 && max > 0 && max < min) {
                    boolQueryBuilder.filter(f -> f.range(r -> r.field("skuPrice").gte(JsonData.of(finalMax)).lte(JsonData.of(finalMin))));
                } else if (min > 0 && max <= 0) {
                    boolQueryBuilder.filter(f -> f.range(r -> r.field("skuPrice").gte(JsonData.of(finalMin))));
                } else if (min <= 0 && max > 0) {
                    boolQueryBuilder.filter(f -> f.range(r -> r.field("skuPrice").lte(JsonData.of(finalMax))));
                }
            } catch (Exception e) {
            }
        }
        if (query.getHasStock() != null && query.getHasStock() == 1) {
            boolQueryBuilder.filter(f -> f.term(t -> t.field("hasStock").value(true)));
        }
        if (!CollectionUtils.isEmpty(query.getAttrs())) {
            try {
                List<String> attrs = query.getAttrs();
                NestedQuery.Builder nested = QueryBuilders.nested().path("attrs");
                for (String attr : attrs) {
                    String[] split = attr.split("_");
                    String attrId = split[0];
                    String[] values = split[1].split(":");
                    List<FieldValue> valueList = Arrays.stream(values).map(value -> FieldValue.of(value)).collect(Collectors.toList());
                    BoolQuery.Builder bool = QueryBuilders.bool();
                    bool.must(m -> m.term(t -> t.field("attrs.attrId").value(attrId)));
                    bool.must(m -> m.terms(t -> t.field("attrs.attrValue").terms(v -> v.value(valueList))));
                    nested.query(q -> q.bool(bool.build()));
                }
                boolQueryBuilder.filter(f -> f.nested(nested.build()));
            } catch (Exception e) {

            }
        }

        queryBuilder.bool(boolQueryBuilder.build());
        builder.query(queryBuilder.build());
        /**
         * 排序 分页 高亮
         */
        if (StringUtils.isNotBlank(query.getSort())) {
            try {
                String[] split = query.getSort().split("_");
                SortOrder sortOrder = split[1].equalsIgnoreCase("desc") ? SortOrder.Desc : SortOrder.Asc;
                if (split[0].equals("saleCount")) {
                    builder.sort(s -> s.field(f -> f.field("saleCount").order(sortOrder)));
                }
                if (split[0].equals("hotScore")) {
                    builder.sort(s -> s.field(f -> f.field("hotScore").order(sortOrder)));
                }
                if (split[0].equals("skuPrice")) {
                    builder.sort(s -> s.field(f -> f.field("skuPrice").order(sortOrder)));
                }
            } catch (Exception e) {
            }
        }
        if (query.getPageNum() == null || query.getPageNum() < 1) {
            query.setPageNum(1);
        }
        builder.size(SearchConstant.PAGE_SIZE).from((query.getPageNum() - 1) * SearchConstant.PAGE_SIZE);

        if (StringUtils.isNotBlank(query.getKeyword())) {
            builder.highlight(h -> h.fields("skuTitle", f -> f.preTags("<b style='color:red'>").postTags("</b>")));
        }
        /**
         * 聚合分析
         */
        builder.aggregations("brands", b -> b.terms(f -> f.field("brandId"))
                .aggregations("brandName", f -> f.terms(t -> t.field("brandName")))
                .aggregations("brandImg", f -> f.terms(t -> t.field("brandImg"))));

        builder.aggregations("catalogs", f -> f.terms(t -> t.field("catalogId"))
                .aggregations("catalogName", fn -> fn.terms(t -> t.field("catalogName"))));

        builder.aggregations("attrs", a -> a.nested(n -> n.path("attrs"))
                .aggregations("attrId", f -> f.terms(t -> t.field("attrs.attrId"))
                        .aggregations("attrName", f1 -> f1.terms(t -> t.field("attrs.attrName")))
                        .aggregations("attrValue", f1 -> f1.terms(t -> t.field("attrs.attrValue"))))
        );

        return builder.build();
    }
}
