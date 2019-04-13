package com.bowie.notes.mq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Bowie on 2019/4/9 17:10
 **/
public class ConnectionUtils {

    public static Connection getConnection() throws IOException,TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务器地址
        factory.setHost("10.200.9.236");
        // AMQP 端口
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("csi");
        //用户名
        factory.setUsername("ottcsi");
        //密码
        factory.setPassword("oidwf$$#*");

        return factory.newConnection();
    }

}
