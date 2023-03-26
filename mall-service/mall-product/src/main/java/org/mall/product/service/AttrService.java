package org.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.product.dto.AttrDTO;
import org.mall.product.entity.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface AttrService extends IService<Attr> {

    PageResult pageBaseAttr(PageQuery pageQuery, Long catelogId, String type);

    /**
     * 保存属性信息和级联的信息
     * @param attrDto
     */
    void saveAttrAndRelation(AttrDTO attrDto);

    /**
     * 获取attr
     * @param attrId
     * @return
     */
    AttrDTO getAttr(Long attrId);

    /**
     * 更新属性信息和关联
     * @param attrDto
     */
    void updateAttrAndRelation(AttrDTO attrDto);

    /**
     * 根据AttrGroupId获取Attr
     * @param attrGroupId
     * @return
     */
    List<Attr> getByAttrGroupId(Long attrGroupId);
}
