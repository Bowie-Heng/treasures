package com.bowie.notes.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Bowie on 2019/3/27 17:53
 **/
public class MyInvocationHandler implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在通过代理类调用对象方法的时候，被代理对象的每个方法都会被代理。
        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("---------被代理的method名称 ：" + method.getName() + "-----");


        System.out.println("---------根据报名和方法名在xml中找到对应的标签-----");
        System.out.println("---------解析标签执行method相关操作-----");
        System.out.println("---------执行完毕-----");

        return null;
    }

}
