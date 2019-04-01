package com.bowie.notes.thread;

import java.util.concurrent.Semaphore;

/**
 * Created by Bowie on 2019/3/29 17:17
 * <p>
 * 在new 这个类的时候需要给这个类传递一个参数permits，
 * 这个参数是整数类型，这个参数的意思是同一时间内，
 * 最多允许多少个线程同时执行acquire方法和release方法之间的代码，
 * 如果方法acquire没有参数则默认是一个许可。
 **/
public class SemaphoreTest {

    public static void main(String[] args) {

        int i = 8;      //工人数量

        Semaphore semaphore = new Semaphore(5); //机器数目

        for (int j = 0; j < i; j++) {
            new Worker(semaphore, j).start();
        }
    }

    static class Worker extends Thread {

        Semaphore semaphore;

        int workerNum;

        Worker(Semaphore semaphore, int j) {
            workerNum = j;
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人" + workerNum + "拿到了工具，正在工作");
                Thread.sleep(2000);
                semaphore.release();
                System.out.println("工人" + workerNum + "工作完毕，交还了机器");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
