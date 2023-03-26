package org.mall.ware.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Queue stockDelayQueue() {
        return QueueBuilder.durable("stock.delay.queue")
                .lazy()
                .ttl(60000)
                .deadLetterExchange("stock-event-exchange")
                .deadLetterRoutingKey("stock.release.stock")
                .build();
    }
    @Bean
    public Queue stockReleaseStockQueue() {
        return QueueBuilder.durable(WareConstant.MQ_QUEUE_NAME)
                .lazy()
                .build();
    }
    @Bean
    public Exchange stockEventExchange() {
        return ExchangeBuilder.topicExchange(WareConstant.MQ_EXCHANGE_NAME)
                .durable(true)
                .build();
    }
    @Bean
    public Binding stockLockedOrder() {
        return BindingBuilder.bind(stockDelayQueue())
                .to(stockEventExchange())
                .with("stock.locked")
                .noargs();
    }
    @Bean
    public Binding orderReleaseOrder() {
        return BindingBuilder.bind(stockReleaseStockQueue())
                .to(stockEventExchange())
                .with("stock.release.#")
                .noargs();
    }
}