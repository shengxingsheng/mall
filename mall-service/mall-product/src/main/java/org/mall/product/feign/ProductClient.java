package org.mall.product.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.product.entity.SkuInfo;
import org.mall.product.entity.SpuInfo;
import org.mall.product.service.ProductClientService;
import org.mall.product.service.SkuInfoService;
import org.mall.product.service.SpuInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/26
 */
@RestController
public class ProductClient implements IProductClient{

    private final ProductClientService productClientService;
    private final SkuInfoService skuInfoService;
    private final SpuInfoService spuInfoService;

    public ProductClient(ProductClientService productClientService, SkuInfoService skuInfoService, SpuInfoService spuInfoService) {
        this.productClientService = productClientService;
        this.skuInfoService = skuInfoService;
        this.spuInfoService = spuInfoService;
    }

    @GetMapping("/product/sku-info/{skuId}/sku-name")
    @Override
    public ResponseEntity<String> getSkuNameById(@PathVariable Long skuId) {
        return ResponseEntity.ok(productClientService.getSkuNameById(skuId));
    }

    @Override
    public ResponseEntity<SkuInfo> getSkuInfo(Long skuId) {
        return ResponseEntity.ok(productClientService.getSkuInfo(skuId));
    }

    @Override
    public ResponseEntity<List<String>> getSaleAttrString(Long skuId) {

        return ResponseEntity.ok(productClientService.getSaleAttrString(skuId));
    }

    @Override
    public ResponseEntity<Map<Long, BigDecimal>> getSkuPrices(List<Long> skuIds) {
        return ResponseEntity.ok(skuInfoService.getPrices(skuIds));
    }

    @Override
    public ResponseEntity<SpuInfo> getSpuInfo(Long skuId) {
        SpuInfo spuInfo = spuInfoService.getBySkuId(skuId);
        return ResponseEntity.ok(spuInfo);
    }

}
