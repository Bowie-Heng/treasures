package com.bowie.notes.zookeeper.origin;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Bowie on 2019/4/8 14:49
 **/
public enum ZKConnection {

    INSTANCE();

    private ZooKeeper zoo;

    //这里 CountDownLatch 用于停止（等待）主进程，直到客户端与ZooKeeper集合连接。
    //ZooKeeper集合通过监视器回调来回复连接状态。
    //一旦客户端与ZooKeeper集合连接，监视器回调就会被调用，并且监视器回调函数调用CountDownLatch的countDown方法来释放锁，在主进程中await。
    final CountDownLatch connectedSignal = new CountDownLatch(1);

    // Method to connect zookeeper ensemble.
    public ZooKeeper connect(String host) throws IOException, InterruptedException {

        zoo = new ZooKeeper(host, 5000, new Watcher() {

            public void process(WatchedEvent we) {

                if (we.getState() == Event.KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            }
        });

        connectedSignal.await();

        return zoo;
    }

    // Method to disconnect from zookeeper server
    public void close() throws InterruptedException {
        zoo.close();
    }
}
