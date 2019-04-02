package com.bowie.notes.thread;

/**
 * Created by Bowie on 2019/3/29 18:23
 * <p>
 * ThreadLocal提供了线程局部变量。它能使其中的值与线程关联起来
 * 下面的代码中，在main线程中设置的值，就算把threadLocal传入到另一个线程，
 * 另一个线程无法取到在main线程中赋值的值
 **/
public class ThreadLocalTest {

    public static void main(String[] args) {

        final ThreadLocal<String> threadLocal = new ThreadLocal<String>();

        threadLocal.set(Thread.currentThread().getName());

        new Thread(new Runnable() {
            public void run() {
                System.out.println("进入线程" + Thread.currentThread().getName() + "，ThreadLocal.get" + "的值为" + threadLocal.get());
            }
        }).start();

        System.out.println("进入线程" + Thread.currentThread().getName() + "，ThreadLocal.get" + "的值为" + threadLocal.get());

    }

}
