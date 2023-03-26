package org.mall.search.service;

import org.mall.search.query.SearchQuery;
import org.mall.search.vo.SearchVo;

/**
 * @author sxs
 * @since 2023/2/8
 */
public interface MallSearchService {
    /**
     * 商品搜索
     *
     * @param query
     * @return
     */
    SearchVo search(SearchQuery query);
}
