package com.bowie.notes.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Bowie on 2019/3/28 13:56
 *
 * 一个客户端和服务端都要实现的接口
 *
 **/
public interface OperationService extends Remote {

    /**
     *  远程接口的方法必须抛出RemoteException，因为网络听信不稳定，不能吃掉异常
     * @param a
     * @param b
     * @return
     * @throws RemoteException
     */
    int add(int a, int b) throws RemoteException;
}
