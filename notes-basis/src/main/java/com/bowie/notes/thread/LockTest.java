package com.bowie.notes.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Bowie on 2019/3/29 14:45
 * <p>
 * 排他锁的使用样例 @TODO 源码解析内容迁移
 **/
public class LockTest {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    //定义两个线程分别调用LockTest的sync方法
    public static void main(String[] args) {

        final LockTest test = new LockTest();

        new Thread(new Runnable() {
            public void run() {
                test.syncMethod();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                test.syncMethod();
            }
        }).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test.lock.lock();
        test.condition.signalAll();
        test.lock.unlock();

    }

    public void syncMethod() {

        lock.lock();

        System.out.println(Thread.currentThread().getName() + "获取到锁");

        try {

            System.out.println(Thread.currentThread().getName() + "调用notify，唤醒在wait的线程");
            Thread.sleep(2000);
            condition.signalAll();

            System.out.println(Thread.currentThread().getName() + "调用wait方法释放了锁");
            condition.await();
            Thread.sleep(2000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "被唤醒，执行完毕，释放锁");
        lock.unlock();

    }

}
