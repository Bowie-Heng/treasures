package com.bowie.notes.io.nio;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Bowie on 2019/4/2 17:17
 * <p>
 * 使用netty构造客户端
 **/
public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            //创建 Bootstrap
            Bootstrap b = new Bootstrap();

            //指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
            b.group(group)
                    //适用于 NIO 传输的Channel 类型
                    .channel(NioSocketChannel.class)
                    //设置服务器的InetSocketAddress
                    .remoteAddress(new InetSocketAddress("localhost", 8080))
                    //在创建Channel时，向 ChannelPipeline中添加一个 EchoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandle());
                        }
                    });

            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();

            //阻塞，直到Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }


    }

    //建立套接字SocketChannel，以及对应的管道pipeline；
    //启动客户端线程，将SocketChannel注册到客户端线程持有的selector中，并将注册返回的selectionKey赋值给SocketChannel关联的selectionKey变量；
    //触发channelRegistered事件；
    //channelRegistered事件由ChannelInitializer的channelRegistered方法响应：将客户端自定义的处理器（譬如EchoClientHandler）按顺序加入到管道中；
    //向服务端发起connect请求，并将SocketChannel关联的selectionKey的OP_CONNECT位置为1；
    //开始三次握手，客户端线程正在运行的select循环检测到了该SocketChannel的CONNECT事件就绪，则将关联的selectionKey的OP_CONNECT位置为0，再通过调用finishConnect完成连接的建立；
    //触发channelActive事件；
    //channelActive事件由EchoClientHandler的channelActive方法响应，通过调用ctx.writeAndFlush方法将消息发往服务端；
    //首先将消息存储到ChannelOutboundBuffer中；（如果ChannelOutboundBuffer存储的所有未flush的消息的大小超过高水位线writeBufferHighWaterMark（默认值为64 * 1024），则会触发ChannelWritabilityChanged事件）
    //然后将消息从ChannelOutboundBuffer中flush到套接字的发送缓冲区中；（如果ChannelOutboundBuffer存储的所有未flush的消息的大小小于低水位线，则会触发ChannelWritabilityChanged事件）

}
