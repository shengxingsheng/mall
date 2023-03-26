package org.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.product.dto.AttrGroupDTO;
import org.mall.product.entity.AttrAttrgroupRelation;
import org.mall.product.entity.AttrGroup;
import org.mall.product.dto.AttrGroupWithAttrDTO;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface AttrGroupService extends IService<AttrGroup> {

    PageResult getPage(PageQuery pageQuery,Long catelogId);

    AttrGroupDTO getDtoById(Long attrGroupId);

    /**
     * 根据catelogId获取属性分组信息
     * @param catelogId
     * @return
     */
    PageResult pageByCatelogId(PageQuery pageQuery,Long catelogId);

    /**
     * 删除属性关联
     * @param relations
     */
    void deleteAtrrRelation(List<AttrAttrgroupRelation> relations);

    /**
     * 获取没有被同分类下属性组关联的属性
     * @param pageQuery
     * @param attrGroupId
     * @return
     */
    PageResult getNotRelationAttr(PageQuery pageQuery, Long attrGroupId);

    /**
     * 删除属性组
     * @param attrGroupIds
     */
    void deleteAttrGroup(List<Long> attrGroupIds);

    List<AttrGroupWithAttrDTO> getAttrGroupWithAttr(Long catalogId);
}
