package org.mall.ware.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.pojo.ResponseEntity;
import org.mall.ware.dto.FareDTO;
import org.mall.ware.dto.WareSkuLockDTO;
import org.mall.ware.service.WareInfoService;
import org.mall.ware.service.WareSkuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/29
 */
@Slf4j
@RestController
@Tag(name = "WareClient")
public class WareClient implements IWareClient {

    private final WareSkuService wareSkuService;
    private final WareInfoService wareInfoService;

    public WareClient(WareSkuService wareSkuService, WareInfoService wareInfoService) {
        this.wareSkuService = wareSkuService;
        this.wareInfoService = wareInfoService;
    }

    @Operation(summary = "过滤出有库存的id")
    @PostMapping("/ware/waresku/stock/state")
    @Override
    public ResponseEntity<List<Long>> filterByStock(@RequestBody List<Long> skuIdList) {
        return ResponseEntity.ok(wareSkuService.filterByStock(skuIdList));
    }

    @Override
    public ResponseEntity<Map<Long, Boolean>> hasStock(List<Long> skuIds) {
        return ResponseEntity.ok(wareSkuService.hasStock(skuIds));
    }

    @Operation(description = "获取运费信息")
    @Override
    public ResponseEntity<FareDTO> fare(Long addrId) {
        return ResponseEntity.ok(wareInfoService.getFare(addrId));
    }

    @Override
    public ResponseEntity<Void> orderLockStock(WareSkuLockDTO wareSkuLockDTO) {
        wareSkuService.orderLockStock(wareSkuLockDTO);
        return ResponseEntity.ok();
    }

}
