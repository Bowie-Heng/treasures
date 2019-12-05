package com.bowie.notes.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;


@Configuration
@Slf4j
public class RabbitMqConfig {

    //定义一个消息队列
    @Bean
    public Queue queue() {
        return new Queue("queue");
    }

    //定义一个交换机
    @Bean
    public Exchange directExchange() {
        return new DirectExchange("exchange");
    }

    //绑定交换机和消息队列
    @Bean
    Binding binding(@Qualifier("queue") Queue queue, @Qualifier("directExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routingKey").noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate();

        rabbitTemplate.setConnectionFactory(connectionFactory);

        //消息发送失败返回到队列中,配置文件需要配置publisher-returns：true
        rabbitTemplate.setMandatory(true);

        //消息确认的回调,配置文件需要配置publisher-confirms：true
        //message 从 producer 到 rabbitmq broker cluster 则会返回一个 confirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                //每个发送的消息都需要配备一个 CorrelationData 相关数据对象，CorrelationData 对象内部只有一个 id 属性，用来表示当前消息唯一性
                //发送的时候设置correlationData,这里才会有值,不然就是null
                log.info("消息发送到exchange成功,id:{}", correlationData != null ? correlationData.getId() : null);
            } else {
                log.error("消息发送到exchange失败,原因:{}", cause);
            }
        });
        //消息只要被 rabbitmq broker 接收到就会执行 confirmCallback，如果是 cluster 模式，需要所有 broker 接收到才会调用 confirmCallback。

        //被 broker 接收到只能表示 message 已经到达服务器，并不能保证消息一定会被投递到目标 queue 里。所以需要用到接下来的 returnCallback 。

        //消费发送失败的回调,配置文件需要配置publisher-returns：true
        //message 从 exchange->queue 投递失败则会返回一个 returnCallback 。
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.error("消息:{}发送失败,应答码:{},原因:{},交换机:{},路由键:{}", correlationId, replyCode, replyText, exchange, routingKey);
        }));

        return rabbitTemplate;

    }


}
