package org.mall.ware.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.mall.common.pojo.ResponseEntity;
import org.mall.ware.dto.FareDTO;
import org.mall.ware.dto.WareSkuLockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/29
 */
@FeignClient("mall-ware")
public interface IWareClient {
    @PostMapping("/ware/waresku/stock/state")
    ResponseEntity<List<Long>> filterByStock(@RequestBody List<Long> skuIdList);

    @PostMapping("/ware/sku/hasStock")
    ResponseEntity<Map<Long,Boolean>> hasStock(@RequestBody List<Long> skuIds);

    @GetMapping("/ware/wareinfo/fare")
    ResponseEntity<FareDTO> fare(@RequestParam("addrId") Long addrId);

    @Operation(summary = "锁定库存")
    @PostMapping("/ware/waresku/lock/order")
    ResponseEntity<Void> orderLockStock(@RequestBody WareSkuLockDTO wareSkuLockDTO);
}
