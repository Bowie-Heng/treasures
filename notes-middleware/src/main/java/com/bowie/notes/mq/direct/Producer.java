package com.bowie.notes.mq.direct;

import com.bowie.notes.mq.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by Bowie on 2019/4/11 16:56
 * 根据指定的路由键发送到对应的消息队列中
 * 在生产者这里声明exchange和routingKey并且与queue绑定
 * 消费者只需绑定某个队列即可
 **/
public class Producer {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange,路由模式声明direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 声明交换机
        String exchangeName = "test_exchange_direct";
        channel.exchangeDeclare(exchangeName, "direct");

        //交换机与queue绑定
        String queueNameA = "test_queue_direct_A";
        String queueNameB = "test_queue_direct_B";

        // 声明队列(如果没有，会创建出来，如果有了，获取这个队列)
        AMQP.Queue.DeclareOk queueA = channel.queueDeclare(queueNameA, false, false, false, null);

        //这里获取到queueA的时候可以做一些事情，比如我们常说的“削峰”
        if (queueA.getMessageCount() >= 100) {
            throw new Exception("队列满了，请返回前端一个友好页面");
        }

        String routingKeyA = "A";
        String routingKeyB = "B";

        /*
         * 绑定队列到交换机
         * 参数1：队列的名称
         * 参数2：交换机的名称
         * 参数3：routingKey
         */
        channel.queueBind(queueNameA, exchangeName, routingKeyA);
        channel.queueBind(queueNameB, exchangeName, routingKeyB);

        // 消息内容
        String message = "这是消息B";
        channel.basicPublish(EXCHANGE_NAME, routingKeyB, null, message.getBytes());
        String messageA = "这是消息A";
        channel.basicPublish(EXCHANGE_NAME, routingKeyA, null, messageA.getBytes());
        System.out.println(" [生产者] Sent '" + message + "'");

        //怎么确定消息都已经发布成功了
        //尽量采用这种模式，而不要用事务模式，它非常消耗性能
        boolean success = channel.waitForConfirms();
        if (success) {
            System.out.println("全部发送成功了");
        }

        channel.close();
        connection.close();
    }
}
