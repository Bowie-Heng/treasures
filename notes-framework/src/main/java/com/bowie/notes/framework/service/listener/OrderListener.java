package com.bowie.notes.framework.service.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "queue",
            durable = "true", ignoreDeclarationExceptions = "true"),
            exchange = @Exchange(value = "exchange",
                    durable = "true",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.DIRECT),
            key = "routingKey"))
    public void process(Message message, Channel channel) throws Exception{
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("收到消息:{}", new String(message.getBody()));
    }
}
