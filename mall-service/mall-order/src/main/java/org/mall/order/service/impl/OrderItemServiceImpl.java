package org.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mall.order.entity.Order;
import org.mall.order.entity.OrderItem;
import org.mall.order.mapper.OrderItemMapper;
import org.mall.order.service.OrderItemService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @RabbitListener(queues = {"hello.queue"})
    public void listener(Order order) {
        System.out.println(order);
    }
}
