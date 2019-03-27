package com.bowie.notes.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by Bowie on 2019/3/27 17:57
 *
 * 模拟mybatis代理接口执行sql
 *
 **/
public class TestDynamicProxy {

    public static void main(String[] args) {

        // 生成接口的代理对象
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{UserMapper.class}, new MyInvocationHandler());

        //执行插入操作
        userMapper.insert();
    }

}
