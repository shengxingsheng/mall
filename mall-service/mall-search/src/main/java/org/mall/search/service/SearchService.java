package org.mall.search.service;

import org.mall.search.dto.SkuEsDTO;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/30
 */
public interface SearchService {
    /**
     * 批量保存文档
     * @param skuEsDTOs
     */
    void saveProduct(List<SkuEsDTO> skuEsDTOs) ;
}
