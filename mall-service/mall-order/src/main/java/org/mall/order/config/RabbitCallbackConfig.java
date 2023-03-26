package org.mall.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;


/**
 * @author sxs
 * @since 2023/2/18
 */
@Configuration
@Slf4j
public class RabbitCallbackConfig {
    public RabbitCallbackConfig(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("id:{},ack:{},causer:{}", correlationData.toString(), ack, cause);
                } else {
                    log.error("id:{},ack:{},causer:{}", correlationData.toString(), ack, cause);
                }
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                Message message = returned.getMessage();
                log.error("replyCode:{},replyText:{},exchange:{},routingKey:{},message:{}", returned.getReplyCode(), returned.getReplyText(), returned.getExchange(), returned.getRoutingKey(), message.toString());
            }
        });
    }
}
