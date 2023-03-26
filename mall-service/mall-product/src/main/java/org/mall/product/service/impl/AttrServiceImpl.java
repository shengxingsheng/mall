package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.util.Assert;
import org.mall.product.constant.ProductConstant;
import org.mall.product.dto.AttrDTO;
import org.mall.product.entity.Attr;
import org.mall.product.entity.AttrAttrgroupRelation;
import org.mall.product.entity.AttrGroup;
import org.mall.product.entity.Category;
import org.mall.product.mapper.AttrAttrgroupRelationMapper;
import org.mall.product.mapper.AttrGroupMapper;
import org.mall.product.mapper.AttrMapper;
import org.mall.product.mapper.CategoryMapper;
import org.mall.product.service.AttrService;
import org.mall.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {
    private AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    private CategoryMapper categoryMapper;
    private AttrGroupMapper attrGroupMapper;
    private CategoryService categoryService;
    public AttrServiceImpl(AttrAttrgroupRelationMapper attrAttrgroupRelationMapper, CategoryMapper categoryMapper, AttrGroupMapper attrGroupMapper, CategoryService categoryService) {
        this.attrAttrgroupRelationMapper = attrAttrgroupRelationMapper;
        this.categoryMapper = categoryMapper;
        this.attrGroupMapper = attrGroupMapper;
        this.categoryService = categoryService;
    }

    @Override
    public PageResult pageBaseAttr(PageQuery pageQuery, Long catelogId, String type) {
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        Assert.notBlank(type,"属性类型不能为空");
        //指定type
        Byte attrType = type.equals(ProductConstant.ATTR_TYPE_BASE_STRING) ? ProductConstant.ATTR_TYPE_BASE : ProductConstant.ATTR_TYPE_SALE;
        wrapper.eq(Attr::getAttrType, attrType);
        //指定分类
        wrapper.eq(catelogId != null && catelogId != 0, Attr::getCatelogId, catelogId);
        //指定关键字
        wrapper.and(StringUtils.isNotBlank(pageQuery.getKey()), w ->
            w.eq(Attr::getAttrId, pageQuery.getKey())
                    .or().like(Attr::getAttrName, pageQuery.getKey())
        );
        Page<Attr> attrPage = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        this.page(attrPage, wrapper);
        List<AttrDTO> collect = attrPage.getRecords().stream().map(attr -> {
            AttrDTO attrDTO = new AttrDTO();
            BeanUtils.copyProperties(attr, attrDTO);
            //设置分类名
            Optional<Category> categoryOptional = Optional.ofNullable(categoryMapper.selectById(attr.getCatelogId()));
            categoryOptional.ifPresent(category -> attrDTO.setCatelogName(category.getName()));
            //基本属性需要设置分组名
            if (attrType.equals(ProductConstant.ATTR_TYPE_BASE)) {
                Optional<AttrAttrgroupRelation> relationOptional = Optional.ofNullable(attrAttrgroupRelationMapper.selectOne(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrId, attr.getAttrId())));
                relationOptional.ifPresent(relation->{
                    Optional<AttrGroup> attrGroupOptional = Optional.ofNullable(attrGroupMapper.selectById(relation.getAttrGroupId()));
                    attrGroupOptional.ifPresent(attrGroup -> attrDTO.setGroupName(attrGroup.getAttrGroupName()));
                });
            }
            return attrDTO;
        }).collect(Collectors.toList());
        return new PageResult(attrPage,collect);
    }

    @Override
    public void saveAttrAndRelation(AttrDTO attrDto) {
        this.save(attrDto);
        Optional<Long> attrGroupIdOptional = Optional.ofNullable(attrDto.getAttrGroupId());
        boolean isBase = attrDto.getAttrType().equals(ProductConstant.ATTR_TYPE_BASE);
        //有 attrGroupId并且是base类型才新增关联
        if (attrGroupIdOptional.isPresent() && isBase){
            AttrAttrgroupRelation relation = new AttrAttrgroupRelation();
            relation.setAttrId(attrDto.getAttrId());
            relation.setAttrGroupId(attrGroupIdOptional.get());
            //保存关联信息
            attrAttrgroupRelationMapper.insert(relation);
        }
    }

    @Override
    public AttrDTO getAttr(Long attrId) {
        Assert.notNull(attrId,"attrId不能为null");
        Optional<Attr> optionalAttr = Optional.ofNullable(this.getById(attrId));
        AttrDTO attrDTO = new AttrDTO();
        optionalAttr.ifPresent(attr -> {
            BeanUtils.copyProperties(attr,attrDTO);
            Optional<AttrAttrgroupRelation> relationOptional = Optional.ofNullable(attrAttrgroupRelationMapper.selectOne(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrId, attrId)));
            relationOptional.ifPresent(relation-> attrDTO.setAttrGroupId(relation.getAttrGroupId()));
            Long[] catelogPath = categoryService.findCatelogPath(attr.getCatelogId());
            attrDTO.setCatelogPath(catelogPath);
        });
        return attrDTO;
    }

    @Override
    public void updateAttrAndRelation(AttrDTO attrDto) {
        Assert.notNull(attrDto);
        Assert.notNull(attrDto.getAttrId());
        this.updateById(attrDto);
        //更新属性组关联表
        Optional<Long> attrGroupIdOptional = Optional.ofNullable(attrDto.getAttrGroupId());
        boolean isBase = attrDto.getAttrType().equals(ProductConstant.ATTR_TYPE_BASE);
        //有attrGroupid并且是base才更新关联
        if (attrGroupIdOptional.isPresent() && isBase) {
            AttrAttrgroupRelation relation = new AttrAttrgroupRelation();
            relation.setAttrGroupId(attrGroupIdOptional.get());
            Long count = attrAttrgroupRelationMapper.selectCount(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrId, attrDto.getAttrId()));
            if (count > 0) {
                attrAttrgroupRelationMapper.update(relation,
                        Wrappers.<AttrAttrgroupRelation>lambdaUpdate().eq(AttrAttrgroupRelation::getAttrId,attrDto.getAttrId()));
            }else {
                relation.setAttrId(attrDto.getAttrId());
                attrAttrgroupRelationMapper.insert(relation);
            }
        }
    }

    @Override
    public List<Attr> getByAttrGroupId(Long attrGroupId) {
        Optional<Long> optional = Optional.ofNullable(attrGroupId);
        if (!optional.isPresent()){
            return Collections.emptyList();
        }
        List<AttrAttrgroupRelation> list = attrAttrgroupRelationMapper.selectList(Wrappers.<AttrAttrgroupRelation>lambdaQuery().eq(AttrAttrgroupRelation::getAttrGroupId, attrGroupId));
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List<Long> attrIds = list.stream().map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        return this.listByIds(attrIds);
    }
}
