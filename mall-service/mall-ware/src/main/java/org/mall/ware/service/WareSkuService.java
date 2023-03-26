package org.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.common.pojo.PageResult;
import org.mall.order.entity.Order;
import org.mall.ware.dto.WareSkuLockDTO;
import org.mall.ware.dto.mq.StockLockDTO;
import org.mall.ware.entity.WareSku;
import org.mall.ware.query.WareSkuPageQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface WareSkuService extends IService<WareSku> {

    /**
     * 分页查询
     * @param wareSkuPageQuery
     * @return
     */
    PageResult page(WareSkuPageQuery wareSkuPageQuery);

    /**
     * 更新库存
     * @param wareSkus
     */
    void addStock(List<WareSku> wareSkus);

    /**
     * 新增商品库存
     * @param wareSku
     */
    void saveWareSku(WareSku wareSku);

    /**
     * 根据库存过滤
     * @param skuIdList
     * @return List<SkuStockStateDTO>
     */
    List<Long> filterByStock(List<Long> skuIdList);


    /**
     * 判断是否有库存
     * @param skuIds
     * @return Map
     */
    Map<Long, Boolean> hasStock(List<Long> skuIds);

    /**
     * 为某个订单锁定库存
     * @param wareSkuLockDTO
     * @return
     */
    void orderLockStock(WareSkuLockDTO wareSkuLockDTO);

    /**
     * 解锁库存
     * @param stockLockDTO
     */
    void unlockStock(StockLockDTO stockLockDTO);
    /**
     * 收到关单消息 解锁库存
     * @param order
     */
    void unlockStock(Order order);

    /**
     * 收到秒杀关单消息 解锁库存
     * @param seckillOrderDTO
     */
    void unlockSeckillStock(SeckillOrderDTO seckillOrderDTO);
}
