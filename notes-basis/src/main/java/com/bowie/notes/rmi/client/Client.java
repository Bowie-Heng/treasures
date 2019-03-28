package com.bowie.notes.rmi.client;

import com.bowie.notes.rmi.OperationService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Bowie on 2019/3/28 14:05
 *
 * 客户端远程调用服务端的add方法
 *
 **/
public class Client {

    public static void main(String[] args) {

        try {

            OperationService operationService = (OperationService) Naming.lookup("rmi://127.0.0.1:1001/operation");

            System.out.println(operationService.add(1,1));


        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

        //编写服务端服务，并将其通过某个服务机的端口暴露出去供客户端调用。
        //编写客户端程序，客户端通过指定服务所在的主机和端口号、将请求封装并序列化，最终通过网络协议发送到服务端。
        //服务端解析和反序列化请求，调用服务端上的服务，将结果序列化并返回给客户端。
        //客户端接收并反序列化服务端返回的结果，反馈给用户。
        //@TODO 源码分析的内容迁移

}
