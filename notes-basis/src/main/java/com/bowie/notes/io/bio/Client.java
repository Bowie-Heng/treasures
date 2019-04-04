package com.bowie.notes.io.bio;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by Bowie on 2019/4/1 19:31
 * socket的客户端
 **/
public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            socket = new Socket("localhost", 8080);
            outputStream = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //这里用System.in的方式阻塞住，模拟与服务端的长连接
            while (true) {
                outputStream.write((br.readLine() + "\n").getBytes());
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(socket);
        }

    }
}
