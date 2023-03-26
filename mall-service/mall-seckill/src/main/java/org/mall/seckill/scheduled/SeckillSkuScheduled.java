package org.mall.seckill.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.SeckillConstant;
import org.mall.seckill.service.SeckillService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 秒杀商品定时上架
 *  每天晚上三点：上架最近三天的需要秒杀的商品
 *  当天的 3：00：00 - 23：59；59
 *  明天的 00：00：00 - 23：59：59
 *  后天的 00：00：00 - 23：59：59
 * @author sxs
 * @since 2023/3/7
 */
@Slf4j
@Component
public class SeckillSkuScheduled {

    private final SeckillService seckillService;
    private final RedissonClient redissonClient;
    public SeckillSkuScheduled(SeckillService seckillService, RedissonClient redissonClient) {
        this.seckillService = seckillService;
        this.redissonClient = redissonClient;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void uploadSeckillSkuLatest3Day() throws JsonProcessingException {
        RLock lock = redissonClient.getLock(SeckillConstant.LOCK_UPlOAD);
        lock.lock(10, SECONDS);
        try {
            //重复上架无需处理
            log.info("商品上架");
            seckillService.uploadSeckillSkuLatest3Day();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

}
