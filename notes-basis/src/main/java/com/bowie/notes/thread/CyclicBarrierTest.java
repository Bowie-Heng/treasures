package com.bowie.notes.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Bowie on 2019/3/29 16:14
 * <p>
 * CyclicBarrier的使用
 * CountDownLatch只能使用一次，而CyclicBarrier可以使用reset重复使用
 * 并且CyclicBarrier的构造方法可以直接传入所有线程执行完毕之后执行的线程
 **/
public class CyclicBarrierTest {

    private static volatile int i = 0;

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        public void run() {

            System.out.println("线程执行完毕，结果为：" + i);

            System.out.println("汇总程序执行完毕，唤醒在等待中的线程");

        }
    });

    private synchronized static void add() {
        System.out.println(Thread.currentThread().getName() + "执行了+1操作");
        i += 1;
    }

    public static void main(String[] args) {
        doTest();
    }

    private static void doTest() {
        new Thread(new Runnable() {
            public void run() {
                CyclicBarrierTest.add();
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程被唤醒");
                recycle();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    CyclicBarrierTest.add();
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程被唤醒");
                recycle();
            }
        }).start();
    }

    //如果发现当前栅栏已经被突破，reset栅栏，继续跑任务
    private static void recycle() {
        if (cyclicBarrier.getNumberWaiting() == 0) {
            System.out.println(Thread.currentThread().getName() + "线程check一下栅栏是否被突破,answer is true");
            try {
                System.out.println(Thread.currentThread().getName() + "突破栅栏，准备重启栅栏程序");
                Thread.sleep(2000);
                cyclicBarrier.reset();
                doTest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(Thread.currentThread().getName() + "线程check一下栅栏是否被突破,answer is false");
        }
    }

}
