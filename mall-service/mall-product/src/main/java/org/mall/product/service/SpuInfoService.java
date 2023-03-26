package org.mall.product.service;

import org.mall.common.pojo.PageResult;
import org.mall.product.dto.SpuInfoDTO;
import org.mall.product.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.product.query.SpuPageQuery;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface SpuInfoService extends IService<SpuInfo> {

    /**
     * 保存spu和sku信息
     * @param spuInfoDTO
     */
    void saveSpuAndSku(SpuInfoDTO spuInfoDTO);

    PageResult page(SpuPageQuery spuPageQuery);

    /**
     * 商品上架
     * @param id
     */
    void up(Long id);

    /**
     * 根据skuId设置
     * @param skuId
     * @return
     */
    SpuInfo getBySkuId(Long skuId);
}
