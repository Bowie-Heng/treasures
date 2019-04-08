package com.bowie.notes.io.nio.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Bowie on 2019/4/2 16:05
 * <p>
 * 使用Netty创建非阻塞的server
 **/
public class Server {

    public static void main(String[] args) throws Exception {

        final ServerHandle serverHandle = new ServerHandle();

        //1.创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            //2.创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();

            //ServerBootStrap的方法有:
            // group	设置 ServerBootstrap 要用的 EventLoopGroup
            // channel	设置将要被实例化的 ServerChannel 类
            // option	实例化的 ServerChannel 的配置项
            // childHandler	设置并添加 ChannelHandler
            // bind	绑定 ServerChannel
            b.group(group)
                    //3.指定使用Nio传输Channel
                    .channel(NioServerSocketChannel.class)
                    //4.使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(8080))
                    //5.添加自定义的handle到channel的channelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            //自定义的handle 被标注为@Shareable，所以总是使用同样的实例
                            //这里对于所有的客户端连接来说，都会使用同一个handle，因为其被标注为@Sharable，
                            socketChannel.pipeline().addLast(serverHandle);
                        }
                    });

            //异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();

            System.out.println(Server.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());

            //获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();


        } finally {
            //(8) 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    //建立服务端监听套接字ServerSocketChannel，以及对应的管道pipeline；
    //启动boss线程，将ServerSocketChannel注册到boss线程持有的selector中，并将注册返回的selectionKey赋值给ServerSocketChannel关联的selectionKey变量；
    //在ServerSocketChannel对应的管道中触发channelRegistered事件；
    //绑定IP和端口
    //触发channelActive事件，并将ServerSocketChannel关联的selectionKey的OP_ACCEPT位置为1。
    //客户端发起connect请求后，boss线程正在运行的select循环检测到了该ServerSocketChannel的ACCEPT事件就绪，则通过accept系统调用建立一个已连接套接字SocketChannel，并为其创建对应的管道；
    //在服务端监听套接字对应的管道中触发channelRead事件；
    //channelRead事件由ServerBootstrapAcceptor的channelRead方法响应：为已连接套接字对应的管道加入ChannelInitializer处理器；启动一个worker线程，并将已连接套接字的注册任务加入到worker线程的任务队列中；
    //worker线程执行已连接套接字的注册任务：将已连接套接字注册到worker线程持有的selector中，并将注册返回的selectionKey赋值给已连接套接字关联的selectionKey变量；在已连接套接字对应的管道中触发channelRegistered事件；channelRegistered事件由ChannelInitializer的channelRegistered方法响应：将自定义的处理器（譬如EchoServerHandler）加入到已连接套接字对应的管道中；在已连接套接字对应的管道中触发channelActive事件；channelActive事件由已连接套接字对应的管道中的inbound处理器的channelActive方法响应；将已连接套接字关联的selectionKey的OP_READ位置为1；至此，worker线程关联的selector就开始监听已连接套接字的READ事件了。
    //在worker线程运行的同时，Boss线程接着在服务端监听套接字对应的管道中触发channelReadComplete事件。
    //客户端向服务端发送消息后，worker线程正在运行的selector循环会检测到已连接套接字的READ事件就绪。则通过read系统调用将消息从套接字的接受缓冲区中读到AdaptiveRecvByteBufAllocator（可以自适应调整分配的缓存的大小）分配的缓存中；
    //在已连接套接字对应的管道中触发channelRead事件；
    //channelRead事件由EchoServerHandler处理器的channelRead方法响应：执行write操作将消息存储到ChannelOutboundBuffer中；
    //在已连接套接字对应的管道中触发ChannelReadComplete事件；
    //ChannelReadComplete事件由EchoServerHandler处理器的channelReadComplete方法响应：执行flush操作将消息从ChannelOutboundBuffer中flush到套接字的发送缓冲区中；
}

