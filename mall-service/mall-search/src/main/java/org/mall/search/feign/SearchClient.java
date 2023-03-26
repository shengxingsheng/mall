package org.mall.search.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.ResponseEntity;
import org.mall.search.dto.SkuEsDTO;
import org.mall.search.service.SearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/29
 */
@Tag(name = "SearchClient",description = "")
@RestController
@Validated
@RequestMapping("/search")
public class SearchClient implements ISearchClient {

    private final SearchService searchService;

    public SearchClient(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "向索引product保存一个文档")
    @Override
    @PostMapping("/product")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody List<SkuEsDTO> skuEsDTOs) {
        searchService.saveProduct(skuEsDTOs);
        return ResponseEntity.ok();
    }
}
