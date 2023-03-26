package org.mall.coupon.mapper;

import org.mall.coupon.entity.CouponHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 优惠券领取历史记录 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Mapper
public interface CouponHistoryMapper extends BaseMapper<CouponHistory> {

}
