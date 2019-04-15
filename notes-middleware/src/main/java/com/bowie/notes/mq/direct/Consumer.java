package com.bowie.notes.mq.direct;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Bowie on 2019/4/11 17:00
 * <p>
 * 这里主要关注一下ack机制，也就是如果出现异常，这条消息不要消费，归还队列的机制
 **/
public class Consumer {


    public static void main(String[] argv) throws Exception {

        new Thread(new Runnable() {
            public void run() {
                try {
                    String queueName = "test_queue_direct_A";
                    consume(queueName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    String queueName = "test_queue_direct_B";
                    consume(queueName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private static void consume(String queueName) throws IOException, TimeoutException {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(queueName, false, false, false, null);

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 启用QoS，每次预取5条消息，避免消息处理不过来，全部堆积在本地缓存里
        //channel.basicQos(0, 5, false);

        // 定义队列的消费者
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                try {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println(new String(body, "UTF-8"));
                    //在这里告诉队列，消费成功，可以安全删除这条消息了
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                    //如果发现异常，归还消息
                    try {
                        channel.basicNack(envelope.getDeliveryTag(), false, true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };

        //autoAck设置会false，意思是不要自动告诉MQ消息已经消费。
        channel.basicConsume(queueName, false, consumer);
    }
}
