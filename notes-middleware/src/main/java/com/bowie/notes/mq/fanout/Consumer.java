package com.bowie.notes.mq.fanout;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Bowie on 2019/4/11 15:12
 **/
public class Consumer {


    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) {

        new Thread(new Runnable() {
            public void run() {
                //消息会被发送到这两个队列中
                String queueName = "test_queue_exchange_1";
                consume(queueName);
            }

        }).start();

        new Thread(new Runnable() {
            public void run() {
                String queueName = "test_queue_exchange_2";
                consume(queueName);
            }

        }).start();
    }

    private static void consume(String queueName) {

        try {

            Connection connection = ConnectionUtils.getConnection();

            final Channel channel = connection.createChannel();

            //声明队列
            channel.queueDeclare(queueName, false, false, false, null);

            /*
                   绑定队列到交换机（这个交换机名称一定要和生产者的交换机名相同）
                   参数1：队列名
                   参数2：交换机名
                   参数3：Routing key 路由键
             */
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            //同一时刻服务器只会发一条数据给消费者
            channel.basicQos(1);

            com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body, "UTF-8");
                    System.out.println("收到消息：" + message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queueName, false, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
