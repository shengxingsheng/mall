package org.mall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.search.constant.SearchConstant;
import org.mall.search.dto.SkuEsDTO;
import org.mall.search.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/30
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    private final ElasticsearchClient esClient;

    public SearchServiceImpl(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    @Override
    public void saveProduct(List<SkuEsDTO> skuEsDTOs) {
        if (CollectionUtils.isEmpty(skuEsDTOs)) {
            return;
        }
        BulkRequest.Builder builder = new BulkRequest.Builder();
        for (SkuEsDTO skuEsDTO : skuEsDTOs) {
            builder.operations(op -> op
                    .index(idx -> idx
                            .index(SearchConstant.INDEX_PRODUCT)
                            .id(skuEsDTO.getSkuId().toString())
                            .document(skuEsDTO)
                    )
            );
        }
        try {
            BulkResponse result = esClient.bulk(builder.build());
            if (result.errors()) {
                log.error("商品上架有错误:");
                for (BulkResponseItem item: result.items()) {
                    if (item.error() != null) {
                        log.error("skuId:{},reason:{}",item.id(),item.error().reason());
                    }
                }
                throw new BusinessException(ErrorCode.MIDDLEWARE_SERVICE_ERROR);
            }
        } catch (IOException e) {
            log.error("商品上架有错误:{}",e);
            throw new BusinessException(ErrorCode.SYSTEM_RESOURCE_EXCEPTION);
        }
    }
}
