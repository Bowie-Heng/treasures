package com.bowie.notes.mq.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Bowie on 2019/4/12 16:46
 **/
public class TestRpc {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Client client = new Client();
        System.out.println(client.call("6"));
    }
}
