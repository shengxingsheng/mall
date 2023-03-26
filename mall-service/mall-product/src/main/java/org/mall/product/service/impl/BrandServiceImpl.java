package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.product.entity.Brand;
import org.mall.product.mapper.BrandMapper;
import org.mall.product.service.BrandService;
import org.mall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    private CategoryBrandRelationService categoryBrandRelationService;

    public BrandServiceImpl(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    @Override
    public PageResult page(PageQuery pageQuery) {
        Page<Brand> brandPage = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        LambdaQueryWrapper<Brand> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageQuery.getKey())){
            queryWrapper.like(Brand::getName, pageQuery.getKey())
                    .or().eq(Brand::getBrandId, pageQuery.getKey());
        }

        this.page(brandPage, queryWrapper);
        return new PageResult(brandPage);
    }


    @Override
    public void updateBrandAndCascade(Brand brand) {
        this.updateById(brand);
        if (StringUtils.isNotBlank(brand.getName())){
            categoryBrandRelationService.updateBrandName(brand.getBrandId(), brand.getName());
        }
    }
}
