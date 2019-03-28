package com.bowie.notes.rmi.server;

import com.bowie.notes.rmi.OperationService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Bowie on 2019/3/28 13:59
 **/
public class OperationServiceImpl extends UnicastRemoteObject implements OperationService {


    public OperationServiceImpl() throws RemoteException {
        super();
    }

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
