package org.mall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mall.ware.entity.WareSku;

import java.util.List;

/**
 * <p>
 * 商品库存 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSku> {


    /**
     * 过滤
     * @param skuIdList
     * @return
     */
    List<Long> filterByStock(@Param("skuIdList") List<Long> skuIdList);

    /**
     * 查出商品有库存的仓库id
     * @param skuId
     * @param num
     * @return
     */
    List<Long> listWareIdHasStock(@Param("skuId") Long skuId, @Param("num") Integer num);

    Long lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);

    void unlockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
