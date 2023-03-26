package org.mall.order.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.order.entity.Order;
import org.mall.order.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author sxs
 * @since 2023/3/3
 */
@Slf4j
@Service
@RabbitListener(queues = "order.release.order.queue")
public class OrderReleaseListener {

    private final OrderService orderService;

    public OrderReleaseListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void orderClose(Order order, Channel channel, Message message) throws IOException {
        log.info("收到订单创建成功消息,判断是否关单...,orderSn:{}",order.getOrderSn());
        try {
            orderService.closeOrder(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
    @RabbitHandler
    public void orderClose(SeckillOrderDTO seckillOrderDTO, Channel channel, Message message) throws IOException {
        log.info("收到秒杀订单创建成功消息,判断是否关单...,orderSn:{}",seckillOrderDTO.getOrderSn());
        try {
            orderService.closeOrder(seckillOrderDTO);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
