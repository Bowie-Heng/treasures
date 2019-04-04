package com.bowie.notes.io.bio.server;

/**
 * Created by Bowie on 2019/4/1 14:46
 * <p>
 * 启动器，用来启动http服务器
 **/
public class Bootstrap {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
