package org.mall.coupon.service;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.coupon.entity.SeckillSkuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 秒杀活动商品关联 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelation> {


    PageResult<SeckillSkuRelation> getPage(PageQuery pageQuery, Long promotionSessionId);
}
