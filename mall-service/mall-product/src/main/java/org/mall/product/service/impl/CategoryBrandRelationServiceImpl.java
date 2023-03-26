package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.util.Assert;
import org.mall.product.entity.Brand;
import org.mall.product.entity.Category;
import org.mall.product.entity.CategoryBrandRelation;
import org.mall.product.mapper.BrandMapper;
import org.mall.product.mapper.CategoryBrandRelationMapper;
import org.mall.product.mapper.CategoryMapper;
import org.mall.product.service.CategoryBrandRelationService;
import org.mall.product.dto.BrandDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌分类关联 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements CategoryBrandRelationService {
    private BrandMapper brandMapper;
    private CategoryMapper categoryMapper;

    public CategoryBrandRelationServiceImpl(BrandMapper brandMapper, CategoryMapper categoryMapper) {
        this.brandMapper = brandMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryBrandRelation> listByBrandId(Long brandId) {
        return this.list(Wrappers.<CategoryBrandRelation>lambdaQuery().eq(brandId != null, CategoryBrandRelation::getBrandId, brandId));
    }

    @Override
    public void saveRelation(CategoryBrandRelation categoryBrandRelation) {
        Brand brand = brandMapper.selectById(categoryBrandRelation.getBrandId());
        Assert.notNull(brand,"没有该id["+categoryBrandRelation.getBrandId()+"]的品牌信息");
        categoryBrandRelation.setBrandName(brand.getName());

        Category category = categoryMapper.selectById(categoryBrandRelation.getCatelogId());
        Assert.notNull(category,"没有该id["+categoryBrandRelation.getCatelogId()+"]的分类信息");
        categoryBrandRelation.setCatelogName(category.getName());

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrandName(Long brandId, String name) {
        if (ObjectUtils.isNotEmpty(brandId) && StringUtils.isNotBlank(name)) {
            this.update(Wrappers.<CategoryBrandRelation>lambdaUpdate()
                    .set(CategoryBrandRelation::getBrandName, name)
                    .eq(CategoryBrandRelation::getBrandId, brandId));
        }
    }

    @Override
    public void updateCatelogName(Long catId, String name) {
        if (ObjectUtils.isNotEmpty(catId) && StringUtils.isNotBlank(name)) {
            this.update(Wrappers.<CategoryBrandRelation>lambdaUpdate()
                    .set(CategoryBrandRelation::getCatelogName, name)
                    .eq(CategoryBrandRelation::getCatelogId,catId));
        }
    }

    @Override
    public List<CategoryBrandRelation> getByCatId(Long catId) {
        return this.list(Wrappers.<CategoryBrandRelation>lambdaQuery().eq(CategoryBrandRelation::getCatelogId, catId));
    }

    @Override
    public List<BrandDTO> getBrandNameByCatId(Long catId) {
        Assert.notNull(catId,"catId不能为null");
        List<CategoryBrandRelation> relations = this.getByCatId(catId);
        if (CollectionUtils.isEmpty(relations)){
            return Collections.emptyList();
        }
        return relations.stream().map(relation -> {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setBrandId(relation.getBrandId());
            brandDTO.setBrandName(relation.getBrandName());
            return brandDTO;
        }).collect(Collectors.toList());
    }
}
