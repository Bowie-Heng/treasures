package com.bowie.notes.mq.topics;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Bowie on 2019/4/11 17:00
 **/
public class Consumer {


    public static void main(String[] argv) throws Exception {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String queueName = "test_queue_topic_1";
                    String routingKey = "order.*";
                    consume(queueName, routingKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    String queueName = "test_queue_topic_2";
                    String routingKey = "order.insert";
                    consume(queueName, routingKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static void consume(String queueName, String routingKey) throws IOException, TimeoutException {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(queueName, false, false, false, null);

        // 声明交换机
        String exchangeName = "test_exchange_topic";
        channel.exchangeDeclare(exchangeName, "topic");

        /*
         * 绑定队列到交换机
         * 参数1：队列的名称
         * 参数2：交换机的名称
         * 参数3：routingKey
         */
        channel.queueBind(queueName, exchangeName, routingKey);

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println(new String(body, "UTF-8"));
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
