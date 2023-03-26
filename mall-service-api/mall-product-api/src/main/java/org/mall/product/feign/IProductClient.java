package org.mall.product.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.mall.common.pojo.ResponseEntity;
import org.mall.product.entity.SkuInfo;
import org.mall.product.entity.SpuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/26
 */
@FeignClient("mall-product")
public interface IProductClient {

    /**
     * 获取skuName
     * @param skuId
     * @return
     */
    @GetMapping("/product/sku-info/{skuId}/sku-name")
    ResponseEntity<String> getSkuNameById(@PathVariable(value = "skuId") Long skuId);

    /**
     * 获取SkuInfo
     * @param skuId
     * @return
     */
    @GetMapping("/product/skuInfo/{skuId}")
    ResponseEntity<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 获取销售属性
     * @param skuId
     * @return
     */
    @GetMapping("/product/skuInfo/{skuId}/saleAttr")
    ResponseEntity<List<String>> getSaleAttrString(@PathVariable("skuId") Long skuId);

    /**
     * 批量获取指定skuid的价格
     * @param skuIds
     * @return
     */
    @PostMapping("/product/skuInfo/prices")
    ResponseEntity<Map<Long, BigDecimal>> getSkuPrices(@RequestBody List<Long> skuIds);

    @Operation(summary = "获取sku的spu信息")
    @GetMapping("/product/spuInfo/{skuId}")
    ResponseEntity<SpuInfo> getSpuInfo(@PathVariable("skuId") Long skuId);
}
