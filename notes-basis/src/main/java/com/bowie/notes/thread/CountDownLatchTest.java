package com.bowie.notes.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Bowie on 2019/3/29 16:06
 * <p>
 * new一个CountDownLatch，主线程wait被阻塞，所有子线程把这个CountDownLatch减为0的时候主线程自动被唤醒
 **/
public class CountDownLatchTest {


    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(2);

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "休眠1s");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "--countDown");
                    latch.countDown();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "休眠2s");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "--countDown");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            System.out.println("等待两个线程执行完毕");
            latch.await();
            System.out.println("所有线程执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
