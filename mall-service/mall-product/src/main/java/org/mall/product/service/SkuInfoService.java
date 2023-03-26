package org.mall.product.service;

import org.mall.common.pojo.PageResult;
import org.mall.product.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.product.query.SkuPageQuery;
import org.mall.product.vo.SkuItemVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface SkuInfoService extends IService<SkuInfo> {

    /**
     * 分页查询
     * @param skuPageQuery
     * @return
     */
    PageResult page(SkuPageQuery skuPageQuery);

    /**
     * 商品详情
     * @param skuId
     * @return
     */
    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;

    /**
     * 获取价格
     * @param skuIds
     * @return
     */
    Map<Long, BigDecimal> getPrices(List<Long> skuIds);
}
