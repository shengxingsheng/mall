package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.util.Assert;
import org.mall.product.constant.ProductConstant;
import org.mall.product.dto.AttrGroupDTO;
import org.mall.product.entity.Attr;
import org.mall.product.entity.AttrAttrgroupRelation;
import org.mall.product.entity.AttrGroup;
import org.mall.product.mapper.AttrAttrgroupRelationMapper;
import org.mall.product.mapper.AttrGroupMapper;
import org.mall.product.mapper.AttrMapper;
import org.mall.product.service.AttrGroupService;
import org.mall.product.service.AttrService;
import org.mall.product.service.CategoryService;
import org.mall.product.dto.AttrGroupWithAttrDTO;
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
 * 属性分组 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {
    private final CategoryService categoryService;
    private final AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    private final AttrMapper attrMapper;
    private final AttrService attrService;


    public AttrGroupServiceImpl(CategoryService categoryService, AttrAttrgroupRelationMapper attrAttrgroupRelationMapper, AttrMapper attrMapper, AttrService attrService) {
        this.categoryService = categoryService;
        this.attrAttrgroupRelationMapper = attrAttrgroupRelationMapper;
        this.attrMapper = attrMapper;
        this.attrService = attrService;
    }

    @Override
    public PageResult getPage(PageQuery pageQuery, Long catelogId) {

        Page<AttrGroup> attrGroupPage = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(catelogId != null && catelogId != 0, AttrGroup::getCatelogId, catelogId);
        if (StringUtils.isNotBlank(pageQuery.getKey())) {
            wrapper.and(w -> w.eq(AttrGroup::getAttrGroupId, pageQuery.getKey())
                    .or().like(AttrGroup::getAttrGroupName, pageQuery.getKey()));
        }
        wrapper.orderByAsc(AttrGroup::getSort);

        this.page(attrGroupPage, wrapper);
        return new PageResult(attrGroupPage);
    }

    @Override
    public AttrGroupDTO getDtoById(Long attrGroupId) {
        AttrGroup attrGroup = this.getById(attrGroupId);
        AttrGroupDTO attrGroupDTO = new AttrGroupDTO();
        BeanUtils.copyProperties(attrGroup, attrGroupDTO);
        Optional<Long> catelogId = Optional.ofNullable(attrGroup.getCatelogId());
        catelogId.ifPresent(id -> attrGroupDTO.setCatelogPath(categoryService.findCatelogPath(id)));
        return attrGroupDTO;
    }

    @Override
    public PageResult pageByCatelogId(PageQuery pageQuery, Long catelogId) {

        Page<AttrGroup> page = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        this.page(page, Wrappers.<AttrGroup>lambdaQuery().eq(AttrGroup::getCatelogId, catelogId));
        return PageResult.create(page);
    }

    @Override
    public void deleteAtrrRelation(List<AttrAttrgroupRelation> relations) {
        if (!CollectionUtils.isEmpty(relations)) {
            attrAttrgroupRelationMapper.deleteByAttrIdAndAttrGroupId(relations);
        }
    }

    @Override
    public PageResult getNotRelationAttr(PageQuery pageQuery, Long attrGroupId) {
        Optional<AttrGroup> attrGroupOptional = Optional.ofNullable(this.getById(attrGroupId));
        Page<Attr> attrPage = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        if (!attrGroupOptional.isPresent()) {
            return new PageResult(attrPage);
        }
        Long catelogId = attrGroupOptional.get().getCatelogId();
        List<AttrGroup> attrGroups = this.list(Wrappers.<AttrGroup>lambdaQuery().eq(AttrGroup::getCatelogId, catelogId));
        if (CollectionUtils.isEmpty(attrGroups)) {
            return new PageResult(attrPage);
        }
        List<Long> attrGroupIds = attrGroups.stream().map(AttrGroup::getAttrGroupId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(attrGroupIds)) {
            return new PageResult(attrPage);
        }
        List<AttrAttrgroupRelation> relations = attrAttrgroupRelationMapper.selectList(Wrappers.<AttrAttrgroupRelation>lambdaQuery().in(AttrAttrgroupRelation::getAttrGroupId, attrGroupIds));
        List<Long> attrIds = relations.stream().map(AttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        LambdaQueryWrapper<Attr> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attr::getCatelogId, catelogId)
                .eq(Attr::getAttrType, ProductConstant.ATTR_TYPE_BASE)
                .notIn(!CollectionUtils.isEmpty(attrIds), Attr::getAttrId, attrIds);
        if (StringUtils.isNotBlank(pageQuery.getKey())) {
            wrapper.and(w -> w.eq(Attr::getAttrId, pageQuery.getKey())
                    .or()
                    .like(Attr::getAttrName, pageQuery.getKey()));
        }
        attrMapper.selectPage(attrPage, wrapper);
        return new PageResult(attrPage);
    }

    @Override
    public void deleteAttrGroup(List<Long> attrGroupIds) {
        if (CollectionUtils.isEmpty(attrGroupIds)) {
            return;
        }
        //查看是否关联属性
        List<AttrAttrgroupRelation> relations = attrAttrgroupRelationMapper.selectList(Wrappers.<AttrAttrgroupRelation>lambdaQuery().in(AttrAttrgroupRelation::getAttrGroupId, attrGroupIds));
        if (CollectionUtils.isEmpty(relations)){
            this.removeBatchByIds(attrGroupIds);
        }else {
            //有关联的attrGroupIds
            List<Long> ids = relations.stream().map(AttrAttrgroupRelation::getAttrGroupId).collect(Collectors.toList());
            attrGroupIds = attrGroupIds.stream().filter(attrGroupId->!ids.contains(attrGroupId)).collect(Collectors.toList());
            this.removeBatchByIds(attrGroupIds);
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "属性分组" + ids + "关联属性不能删除");
        }
    }

    @Override
    public List<AttrGroupWithAttrDTO> getAttrGroupWithAttr(Long catalogId) {
        Assert.notNull(catalogId,"catalogId不能为null");
        //获取分类关联的分组
        List<AttrGroup> attrGroups = this.list(new LambdaQueryWrapper<AttrGroup>().eq(AttrGroup::getCatelogId, catalogId).orderByAsc(AttrGroup::getSort));
        if (CollectionUtils.isEmpty(attrGroups)){
            return Collections.emptyList();
        }
        //获取分组管理的属性
       return attrGroups.stream().map(attrGroup -> {
            AttrGroupWithAttrDTO dto = new AttrGroupWithAttrDTO();
            BeanUtils.copyProperties(attrGroup, dto);
            Long groupId = attrGroup.getAttrGroupId();
            List<Attr> attrs = attrService.getByAttrGroupId(groupId);
            dto.setAttrs(attrs);
            return dto;
        }).collect(Collectors.toList());

    }

}
