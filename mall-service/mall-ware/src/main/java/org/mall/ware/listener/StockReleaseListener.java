package org.mall.ware.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.order.entity.Order;
import org.mall.ware.dto.mq.StockLockDTO;
import org.mall.ware.service.WareSkuService;
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
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {
    private final WareSkuService wareSkuService;

    public StockReleaseListener(WareSkuService wareSkuService) {
        this.wareSkuService = wareSkuService;
    }

    /**
     * 库存解锁
     * @param stockLockDTO
     */
    @RabbitHandler
    public void stockRelease(StockLockDTO stockLockDTO, Channel channel, Message message) throws IOException {
        log.info("收到库存锁定成功消息,判断是否需要解锁库存...,taskId:{}",stockLockDTO.getTaskId());
        try {
            wareSkuService.unlockStock(stockLockDTO);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

    /**
     * 关单
     */
    @RabbitHandler
    public void closeOrder(Order order,Channel channel,Message message) throws IOException {
        log.info("收到关单消息,进行解锁库存...,orderSn:{}",order.getOrderSn());
        try {
            wareSkuService.unlockStock(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
    /**
     * 秒杀关单
     */
    @RabbitHandler
    public void closeSekillOrder(SeckillOrderDTO seckillOrderDTO, Channel channel, Message message) throws IOException {
        log.info("收到秒杀关单消息,判断是否返还库存量...,orderSn:{}",seckillOrderDTO.getOrderSn());
        try {
            wareSkuService.unlockSeckillStock(seckillOrderDTO);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
