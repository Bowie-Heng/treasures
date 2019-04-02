package com.bowie.notes.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Bowie on 2019/3/29 17:26
 * <p>
 * 我们使用Executors构建线程池，可以构建的线程池有很多种，但是本质上是实例化不同的ThreadPoolExecutor对象
 * 所以我们这里拿FixedThreadPool举个例子
 * <p>
 * 线程池的线程重用是因为new了一个新线程work，work配合阻塞队列调用我们传入的线程的run方法，实现线程重用
 * <p>
 * 而实现Callable接口可以获取返回数据是因为被包装成了FutureTask，他个get方法在他其中的成员变量没有被赋值成功的时候处于阻塞状态
 *
 * @TODO 源码分析内容迁移
 **/
public class ThreadPoolTest {

    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //定义接受返回内容的List
        List<Future<String>> futureList = new ArrayList<Future<String>>();

        for (int i = 0; i < 8; i++) {
            Future<String> future = executorService.submit(new CallableTest(i));
            futureList.add(future);
        }

        try {
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static class CallableTest implements Callable<String> {

        int taskId;

        CallableTest(int i) {
            taskId = i;
        }

        public String call() throws Exception {
            System.out.println("任务" + taskId + "被执行了");
            Thread.sleep(2000);
            return "任务" + taskId + "结果获取成功";
        }
    }
}
