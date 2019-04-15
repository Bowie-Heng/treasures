package com.bowie.notes.mq.worker;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by Bowie on 2019/4/11 14:25
 * 多个消费者同时绑定一个队列的情况
 **/
public class Producer {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 0; i < 50; i++) {
            String message = "" + i;
            System.out.println("send the message = " + message);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            Thread.sleep(10 * i);
        }
        channel.close();
        connection.close();
    }
}
