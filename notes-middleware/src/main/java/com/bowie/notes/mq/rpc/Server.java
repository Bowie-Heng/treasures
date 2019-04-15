package com.bowie.notes.mq.rpc;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Bowie on 2019/4/12 16:40
 * <p>
 * 当客户端启动时，它会创建一个匿名的独占回调队列。
 * 对于一个RPC请求，客户端通过两个属性发送一条消息：relayTo，设置回调队列；correlationId，为每个请求设置一个唯一值。
 * 消息将被发送到一个rpc_queue队列。
 * RPC工作线程（即，服务器）在该队列上等待请求。当请求出现，他将处理请求并把结果发回给客户端，使用的队列是在replayTo中设置的。
 * 客户端在回调队列上等待响应，当消息出现，它检查关联ID，如果匹配来自请求的关联ID值，返回消息到该应用程序。
 **/
public class Server {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                super.handleDelivery(consumerTag, envelope, properties, body);

                AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();

                String mes = new String(body, "UTF-8");

                int num = Integer.valueOf(mes);

                System.out.println("接收数据：" + num);

                num = fib(num);

                // 返回处理结果队列
                channel.basicPublish("", properties.getReplyTo(), properties1, String.valueOf(num).getBytes());

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

        while (true) {
            synchronized (consumer) {
                try {
                    consumer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
 获取斐波那契数列的第n个值得大小
*/
    private static int fib(int n) {
        System.out.println(n);
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
