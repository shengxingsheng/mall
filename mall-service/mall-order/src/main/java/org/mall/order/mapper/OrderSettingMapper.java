package org.mall.order.mapper;

import org.mall.order.entity.OrderSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单配置信息 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Mapper
public interface OrderSettingMapper extends BaseMapper<OrderSetting> {

}
