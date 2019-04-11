package com.bowie.notes.zookeeper.origin;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Bowie on 2019/4/8 15:03
 **/
public class ZKTest {

    @Test
    public void testCreate() {
        try {

            ZooKeeper zooKeeper = ZKConnection.INSTANCE.connect("localhost");

            ZKUtils zkUtils = new ZKUtils();

            zkUtils.setZk(zooKeeper);

            zkUtils.create("/MyFirstZnode", "MyFirstData".getBytes());

            zooKeeper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetData() {

        try {

            final ZooKeeper zooKeeper = ZKConnection.INSTANCE.connect("localhost");

            final CountDownLatch connectedSignal = new CountDownLatch(1);

            byte[] b = zooKeeper.getData("/MyFirstZnode", new Watcher() {
                //监视器类型的回调函数。当指定的znode的数据改变时，ZooKeeper集合将通过监视器回调进行通知。
                public void process(WatchedEvent we) {
                    if (we.getType() == Event.EventType.None) {
                        switch (we.getState()) {
                            case Expired:
                                connectedSignal.countDown();
                                break;
                        }

                    } else {
                        String path = "/MyFirstZnode";

                        try {
                            byte[] bn = zooKeeper.getData(path,
                                    false, null);
                            String data = new String(bn,
                                    "UTF-8");
                            System.out.println(data);
                            connectedSignal.countDown();

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }, null);

            String data = new String(b, "UTF-8");
            System.out.println(data);
            connectedSignal.await();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testDelete() {
        try {

            ZooKeeper zooKeeper = ZKConnection.INSTANCE.connect("localhost");

            ZKUtils zkUtils = new ZKUtils();

            zkUtils.setZk(zooKeeper);

            zkUtils.delete("/MyFirstZnode");

            zooKeeper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
