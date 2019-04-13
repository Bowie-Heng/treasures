package com.bowie.notes.mq.fanout;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by Bowie on 2019/4/11 15:00
 *
 * Publish/Subscribe
 * 不绑定Routing，生产者的消息会被发送到所有bind的queue上
 **/
public class Producer {

    //交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        /*
             声明exchange交换机
             参数1：交换机名称
             参数2：交换机类型
             参数3：交换机持久性，如果为true则服务器重启时不会丢失
             参数4：交换机在不被使用时是否删除
             参数5：交换机的其他属性
          */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true, true, null);

        String message = "发布的消息";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("生产者 send ：" + message);
        channel.close();
        connection.close();
    }

}
