package org.mall.order.service.impl;

import org.mall.order.entity.OrderSetting;
import org.mall.order.mapper.OrderSettingMapper;
import org.mall.order.service.OrderSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单配置信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements OrderSettingService {

}
