package org.mall.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.mall.product.entity.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mall.product.vo.SkuItemSaleAttrVo;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);

    /**
     * 获取销售属性字符串
     * @param skuId
     * @return
     */
    List<String> getSaleAttrString(@Param("skuId") Long skuId);

}
