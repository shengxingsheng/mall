package org.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mall.product.entity.ProductAttrValue;
import org.mall.product.vo.SpuItemBaseAttrVo;

import java.util.List;

/**
 * <p>
 * spu属性值 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Mapper
public interface ProductAttrValueMapper extends BaseMapper<ProductAttrValue> {

    List<SpuItemBaseAttrVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId);
}
