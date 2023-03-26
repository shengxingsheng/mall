package org.mall.common.constant;

/**
 * @author sxs
 * @since 2023/3/7
 */
public class SeckillConstant {

    /**
     * list结构
     */
    public static final String CACHE_KEY_SESSION = "seckill:sessions:";
    /**
     * hashmap结构
     */
    public static final String CACHE_KEY_SKU = "seckill:skus";
    /**
     * 库存信号量
     */
    public static final String CACHE_KEY_STOCK = "seckill:stock:";
    /**
     * 用户的秒杀记录
     */
    public static final String CACHE_KEY_USER_RECORD = "seckill:user:";

    public static final String LOCK_UPlOAD = "seckill:upload:lock";
}
