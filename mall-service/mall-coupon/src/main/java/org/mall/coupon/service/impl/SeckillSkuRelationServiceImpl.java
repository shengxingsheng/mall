package org.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.coupon.entity.SeckillSkuRelation;
import org.mall.coupon.mapper.SeckillSkuRelationMapper;
import org.mall.coupon.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀活动商品关联 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationMapper, SeckillSkuRelation> implements SeckillSkuRelationService {

    @Override
    public PageResult<SeckillSkuRelation> getPage(PageQuery pageQuery, Long promotionSessionId) {
        Page<SeckillSkuRelation> page = new Page(pageQuery.getPage(), pageQuery.getLimit());
        LambdaQueryWrapper<SeckillSkuRelation> wrapper  = new LambdaQueryWrapper<>();
        wrapper.eq(SeckillSkuRelation::getPromotionSessionId,promotionSessionId);
        if (StringUtils.isNotBlank(pageQuery.getKey())) {
            wrapper.eq(SeckillSkuRelation::getId, pageQuery.getKey())
                    .or().eq(SeckillSkuRelation::getSkuId,pageQuery.getKey());
        }
        wrapper.orderByDesc(SeckillSkuRelation::getCreateTime);
        this.page(page,wrapper);
        return new PageResult<SeckillSkuRelation>(page);
    }
}
