package com.bowie.notes.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * Created by Bowie on 2019/4/9 14:45
 **/
public class LockTest {


    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorUtils.INSTANCE.getClient("localhost");

        //构建互斥锁
        final InterProcessMutex mutex = new InterProcessMutex(client, "/lock");

        //可以理解为下面两个线程是在不同的系统下面的
        new Thread(new Runnable() {
            public void run() {
                try {
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + "：获取到了锁哟");
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        mutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    mutex.acquire();
                    System.out.println(Thread.currentThread().getName() + "：获取到了锁呀");
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        mutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }


    //原理：
    // 1.在/lock下创建 临时的 有序的 子节点，例如第一个客户端为/lock/lock-0000000，第二个为/lock/lock-0000001
    // 2.客户端获取/lock下的子节点列表，判断自己是不是创建的当前子节点列表中序号最小的子节点，是的话，就认为是获取到了锁。
    // 3.否则，监听在自己之前一位的子节点的删除消息，获取子节点变更信息通知后重复步骤2直到获取到了锁
    // 4.执行业务代码
    // 5.删除自己创建的节点释放锁.
}
