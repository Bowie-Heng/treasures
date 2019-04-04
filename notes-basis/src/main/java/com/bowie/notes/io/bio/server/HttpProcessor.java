package com.bowie.notes.io.bio.server;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by Bowie on 2019/4/1 15:16
 * <p>
 * 这里用来处理获取到的http请求（简化）
 * <p>
 * 获取输入信息并且返回的方法这里列举了两种方式
 * 一个用以处理短连接 一个用以处理长连接
 **/
public class HttpProcessor {


    public void process(Socket socket) {


        //在这里我们可以对socket中的信息做处理

        //用浏览器输入localhost:8080
        //会打印http请求的完整信息
        //请求行、请求头、空行、消息体
        //GET /index.html HTTP/1.1
        //Cache-Control:max-age=0
        //Cookie:gsScrollPos=; _ga=GA1.2.329038035.1465891024; _gat=1
        //If-Modified-Since:Sun, 01 May 2016 11:19:03 GMT
        //User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36
        //
        //{...............}

        //我们需要对http请求进行解析，然后封装到request和response中去
        //然后使用反射，如果容器中没有的话，实例化用以处理某个url的servlet，调用init。
        //有的话直接调用该servlet的service @TODO 这两块儿内容比较繁琐，后续补上

        //这里的话我们简化一下，给浏览器返回固定内容

        //长连接相对于短连接，socket不关闭
        //在一次流的交互完成之后，可以先挂起socket，例如放入到socket池中去
        //需要再次交互的时候，直接从池中获取socket，再次进行流交互
        dealLongConnect(socket);

        //短连接的意思就是每次交互信息，都要开启和关闭socket，也就是Tcp连接
        //dealShortConnect(socket);

    }


    //处理短连接方式的请求
    private void dealShortConnect(Socket socket) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            //这里直接构造一个2048的byte数组获取入参
            byte[] buffer = new byte[2048];
            //获取到所有的输入（如果大于2048byte的话会被忽略的）
            int i = inputStream.read(buffer);
            for (int j = 0; j < i; j++) {
                sb.append((char) buffer[j]);
            }

            //打印入参
            System.out.println(sb);

            //返回固定内容
            returnData(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //代码走完之后，socket就关闭了
            //如果想要再次发送数据，需要再次建立socket连接才行
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(socket);
        }

    }


    //处理长连接方式的请求
    private void dealLongConnect(Socket socket) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            //直接构造一个字符reader
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputLine;
            //readLine就是差别，这个readLine方法在流中没有新内容的时候会阻塞住
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
                //这里我们简化一下 获取到空的字符串的时候就表示当前流的第一次请求已经获取完了
                if (inputLine.equalsIgnoreCase("")) {

                    System.out.println(sb.toString());

                    outputStream = socket.getOutputStream();

                    returnData(outputStream);

                    //清空StringBuilder中的内容，供下次数据进来的时候存放
                    sb.delete(0, sb.length());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(socket);
        }

    }

    //这里我们简化一下，返回固定内容
    private void returnData(OutputStream outputStream) throws IOException {

        //这里这个Content-Length一定要填，不然浏览器不知道到底返回的数据是多少，会一直在加载中
        String returnStr = "HTTP/1.1 200 OK\nContent-Type:text/html\nServer:myServer\nServer:myServer\nContent-Length:25\n\n<h1>request received!</h1>";

        System.out.println(returnStr);

        IOUtils.write(returnStr, outputStream, "utf-8");
    }
}
