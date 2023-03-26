package org.mall.seckill.service;

import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;

import java.util.List;

/**
 * @author sxs
 * @since 2023/3/7
 */
public interface SeckillService {
    /**
     * 上架商品
     */
    void uploadSeckillSkuLatest3Day();

    /**
     * 获取当前时间的秒杀商品
     * @return
     */
    List<SeckillSkuInfoDTO> getCurrentSeckillSkus();

    SeckillSkuInfoDTO getSeckillSku(Long skuId);

    /**
     * 秒杀商品
     * @param killId 场次id_skuId
     * @param key 随机码
     * @param num 数量
     * @return
     */
    String kill(String killId, String key, Integer num);
}
