package org.mall.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mall.order.constant.OrderConstant;
import org.mall.ware.constant.WareConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sxs
 */
@Configuration
public class RabbitConfig {

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * 容器中的queue exchange binding 自动到mq中创建
     */
    @Bean
    public Queue orderDelayQueue() {
        return QueueBuilder.durable("order.delay.queue")
                .lazy()
                .ttl(60000)
                .deadLetterExchange("order-event-exchange")
                .deadLetterRoutingKey("order.release.order")
                .build();
    }
    @Bean
    public Queue orderReleaseOrderQueue() {
        return QueueBuilder.durable("order.release.order.queue")
                .lazy()
                .build();
    }

    /**
     * 秒杀订单队列
     * @return
     */
    @Bean
    public Queue seckIllOrderQueue() {
        return QueueBuilder.durable("order.seckill.order.queue")
                .lazy()
                .build();
    }
    @Bean
    public Exchange orderEventExchange() {
        return ExchangeBuilder.topicExchange("order-event-exchange")
                .durable(true)
                .build();
    }
    @Bean
    public Binding orderCreateOrder() {
        return BindingBuilder.bind(orderDelayQueue())
                .to(orderEventExchange())
                .with("order.create.order")
                .noargs();
    }
    @Bean
    public Binding orderReleaseOrder() {
        return BindingBuilder.bind(orderReleaseOrderQueue())
                .to(orderEventExchange())
                .with("order.release.order")
                .noargs();
    }

    @Bean
    public Binding orderReleaseOtherCloseOrder() {
        return new Binding(WareConstant.MQ_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                OrderConstant.MQ_EXCHANGE_NAME,
                "order.release.other.#",
                null);
    }

    /**
     * 秒杀订单绑定关系
     * @return
     */
    @Bean
    public Binding seckillOrder() {
        return new Binding("order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);
    }
}