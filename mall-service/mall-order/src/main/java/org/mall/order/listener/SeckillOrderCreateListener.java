package org.mall.order.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.order.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author sxs
 * @since 2023/3/11
 */
@Slf4j
@Service
@RabbitListener(queues = "order.seckill.order.queue")
public class SeckillOrderCreateListener {

    private final OrderService orderService;

    public SeckillOrderCreateListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void createSeckillOrder(SeckillOrderDTO seckillOrderDTO, Channel channel, Message message) throws IOException {
        log.info("收到秒杀订单创建信息,orderSn:{}",seckillOrderDTO.getOrderSn());
        try {
            orderService.createSeckillOrder(seckillOrderDTO);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            log.error(e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
