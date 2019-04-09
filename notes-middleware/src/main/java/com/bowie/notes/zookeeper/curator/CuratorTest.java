package com.bowie.notes.zookeeper.curator;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * Created by Bowie on 2019/4/8 16:24
 **/
public class CuratorTest {


    @Test
    public void testCreate() {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        try {
            //创建一个初始内容为空的节点
//            client.create().forPath("/China");
            //创建一个初始内容不为空的节点
            client.create().forPath("/Korea/bangzib", "jinzhengen".getBytes());
            //创建一个初始内容为空的临时节点
            client.create().withMode(CreateMode.EPHEMERAL).forPath("America");
            //创建一个初始内容不为空的临时节点，可以实现递归创建
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath("Japan", "xiaoriben".getBytes());


            /**
             *  异步创建节点
             *
             * 注意:如果自己指定了线程池,那么相应的操作就会在线程池中执行,如果没有指定,
             *   那么就会使用Zookeeper的EventThread线程对事件进行串行处理
             * */
            client.create().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + ",code:"
                            + event.getResultCode() + ",type:" + event.getType());
                }
            }, Executors.newFixedThreadPool(10)).forPath("/async-China");


            client.create().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    System.out.println("当前线程：" + Thread.currentThread().getName() + ",code:"
                            + event.getResultCode() + ",type:" + event.getType());
                }
            }).forPath("/async-America");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getData() {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        try {
            byte[] data = client.getData().forPath("/Korea");
            System.out.println(new String(data));
            //传入一个旧的stat变量,来存储服务端返回的最新的节点状态信息
            byte[] data2 = client.getData().storingStatIn(new Stat()).forPath("/Korea");
            System.out.println(new String(data2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void setData() {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        try {
            Stat stat = client.setData().forPath("/Korea");
            client.setData().withVersion(stat.getVersion()).forPath("/Korea", "jinsanpangzi".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteData() {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        try {
//            client.delete().forPath("/China");
            //删除一个节点,并递归删除其所有子节点
            client.delete().deletingChildrenIfNeeded().forPath("/Korea");
            //强制指定版本进行删除
            client.delete().withVersion(4).forPath("/Korea");
            //注意:由于一些网络原因,上述的删除操作有可能失败,使用guaranteed(),
            // 如果删除失败,会记录下来,只要会话有效,就会不断的重试,直到删除成功为止
            client.delete().guaranteed().forPath("/America");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
