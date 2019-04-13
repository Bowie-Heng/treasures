package com.bowie.notes.mq.onefold;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Bowie on 2019/4/11 13:48
 **/
public class Consumer {
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel(1);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        final StringBuffer message = new StringBuffer();
        //自4.0+ 版本后无法再使用QueueingConsumer，而官方推荐使用DefaultConsumer
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.handleDelivery(consumerTag, envelope, properties, body);
                message.append(new String(body, "UTF-8"));
                System.out.println(new String(body, "UTF-8"));
            }
        };
        //监听队列，当b为true时，为自动提交（只要消息从队列中获取，无论消费者获取到消息后是否成功消息，都认为是消息已经成功消费），
        // 当b为false时，为手动提交（消费者从队列中获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，
        // 如果消费者一直没有反馈，那么该消息将一直处于不可用状态。
        //如果选用自动确认,在消费者拿走消息执行过程中出现宕机时,消息可能就会丢失！！）
        //使用channel.basicAck(envelope.getDeliveryTag(),false);进行消息确认
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.out.println(message.toString());
    }
}
