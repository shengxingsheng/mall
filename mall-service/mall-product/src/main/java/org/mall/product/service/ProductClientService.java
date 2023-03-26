package org.mall.product.service;

import org.mall.product.entity.SkuInfo;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/26
 */
public interface ProductClientService {

    /**
     * 获取skuname
     * @param skuId
     * @return
     */
    String getSkuNameById(Long skuId);
    /**
     * 获取CartSkuInfoDTO
     * @param skuId
     * @return
     */
    SkuInfo getSkuInfo(Long skuId);

    List<String> getSaleAttrString(Long skuId);
}
