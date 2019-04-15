package com.bowie.notes.mq.topics;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by Bowie on 2019/4/11 17:23
 * 可以理解为Routing的通配符模式
 **/
public class Producer {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String message = "匹配insert";
        channel.basicPublish(EXCHANGE_NAME, "order.update", false, false, null, message.getBytes());

        channel.close();
        connection.close();
    }
}
