package com.bowie.notes.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Bowie on 2019/3/29 17:48
 * <p>
 * 利用阻塞队列完成经典的生产者消费者模式
 **/
public class ProducerAndCustomer {

    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    private static Lock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    private static boolean stop = false;

    public static void main(String[] args) {

        //生产者线程
        new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    while (!stop) {
                        System.out.println("生产者生产任务中----");
                        Thread.sleep(1000);
                        for (int i = 0; i < 10; i++) {
                            queue.put("任务" + i);
                        }
                        System.out.println("生产者生产任务完毕，唤醒消费者消费任务");
                        condition.signalAll();
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stop = true;
                    lock.unlock();
                }
            }
        }).start();

        //消费者线程
        new Thread(new Runnable() {
            public void run() {
                try {
                    lock.lock();
                    System.out.println("消费者被唤醒，开始消费任务");
                    String result;
                    while (!stop) {
                        while (( result =queue.poll()) != null) {
                            System.out.println(result);
                        }
                        Thread.sleep(1000);
                        System.out.println("消费者消费掉所有任务,唤醒生产者生产任务");
                        condition.signalAll();
                        condition.await();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stop = true;
                    lock.unlock();
                }
            }
        }).start();


    }


}
