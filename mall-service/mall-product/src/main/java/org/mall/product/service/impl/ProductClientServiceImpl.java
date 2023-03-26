package org.mall.product.service.impl;

import org.mall.product.entity.SkuInfo;
import org.mall.product.mapper.SkuInfoMapper;
import org.mall.product.mapper.SkuSaleAttrValueMapper;
import org.mall.product.service.ProductClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author sxs
 * @since 2023/1/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductClientServiceImpl implements ProductClientService {
    private final SkuInfoMapper skuInfoMapper;
    private final SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    public ProductClientServiceImpl(SkuInfoMapper skuInfoMapper, SkuSaleAttrValueMapper skuSaleAttrValueMapper) {
        this.skuInfoMapper = skuInfoMapper;
        this.skuSaleAttrValueMapper = skuSaleAttrValueMapper;

    }

    @Override
    public String getSkuNameById(Long skuId) {
        if (Objects.isNull(skuId)) {
            return "";
        }
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (Objects.isNull(skuInfo)) {
            return "";
        }
        return skuInfo.getSkuName();
    }

    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        return skuInfoMapper.selectById(skuId);
    }

    @Override
    public List<String> getSaleAttrString(Long skuId) {
        return skuSaleAttrValueMapper.getSaleAttrString(skuId);
    }
}
