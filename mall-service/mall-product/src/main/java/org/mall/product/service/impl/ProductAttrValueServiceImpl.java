package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mall.common.util.Assert;
import org.mall.product.entity.ProductAttrValue;
import org.mall.product.entity.SpuInfo;
import org.mall.product.mapper.ProductAttrValueMapper;
import org.mall.product.mapper.SpuInfoMapper;
import org.mall.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {
    private final SpuInfoMapper spuInfoMapper;

    public ProductAttrValueServiceImpl(SpuInfoMapper spuInfoMapper) {
        this.spuInfoMapper = spuInfoMapper;
    }

    @Override

    public List<ProductAttrValue> getBySpuId(Long spuId) {
        if (Objects.isNull(spuId)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<ProductAttrValue>lambdaQuery().eq(ProductAttrValue::getSpuId, spuId));
    }

    @Override
    public void updateBySpuId(Long spuId, List<ProductAttrValue> attrList) {
        SpuInfo spuInfo = spuInfoMapper.selectById(spuId);
        Assert.notNull(spuInfo,"该spu不存在");
        //删除原来的属性
        this.remove(Wrappers.<ProductAttrValue>lambdaUpdate().eq(ProductAttrValue::getSpuId, spuId));
        //新增
        if (!CollectionUtils.isEmpty(attrList)) {
            List<ProductAttrValue> collect = attrList.stream().map(attr -> {
                attr.setSpuId(spuId);
                return attr;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }
}
