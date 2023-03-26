package org.mall.order.service.impl;

import org.mall.order.entity.OrderOperateHistory;
import org.mall.order.mapper.OrderOperateHistoryMapper;
import org.mall.order.service.OrderOperateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
