package com.bowie.notes.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Bowie on 2019/3/28 14:01
 *
 * Rmi功能的服务端
 *
 **/
public class Server {

    public static void main(String[] args) {

        try {

            LocateRegistry.createRegistry(1001);

            Naming.bind("rmi://localhost:1001/operation",new OperationServiceImpl());

            System.out.println("server running....");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }


    }


}
