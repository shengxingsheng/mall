package org.mall.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.mall.product.entity.AttrAttrgroupRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 属性&属性分组关联 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelation> {

    void deleteByAttrIdAndAttrGroupId(@Param("relations") List<AttrAttrgroupRelation> relations);
}
