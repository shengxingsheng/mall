package org.mall.search.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.search.dto.SkuEsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/29
 */
@FeignClient("mall-search")
public interface ISearchClient {
    @PostMapping("/search/product")
    @Validated
    ResponseEntity<Void> saveProduct(@Valid @RequestBody List<SkuEsDTO> skuEsDTOs);
}
