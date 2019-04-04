package com.bowie.notes.io.bio.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Bowie on 2019/4/1 14:44
 * <p>
 * 使用阻塞io完成http服务器
 **/
public class HttpServer implements Runnable {

    boolean stopped;
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stopped) {

            Socket socket;
            try {
                //阻塞到请求来到
                socket = serverSocket.accept();
            } catch (Exception e) {
                continue;
            }
            // 获取到请求之后用processor去处理请求
            HttpProcessor processor = new HttpProcessor();
            processor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        System.out.println("服务器启动成功...");
        thread.start();
    }
}
