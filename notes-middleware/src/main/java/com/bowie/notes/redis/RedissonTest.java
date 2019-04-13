package com.bowie.notes.redis;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bowie on 2019/4/12 17:40
 * 更多使用请参考 https://github.com/redisson/redisson/wiki/目录
 **/
public class RedissonTest {

    private static final String RAtomicName = "genId_";

    Redisson redisson;

    @Before
    public void init() {
        Config config = new Config();
        try {
            //指定编码，默认编码为org.redisson.codec.JsonJacksonCodec
            config.setCodec(new org.redisson.client.codec.StringCodec());
            //指定使用单节点部署方式
            config.useSingleServer().setAddress("redis://10.200.11.247:6379");
            //config.setPassword("password")//设置密码
            //设置对于master节点的连接池中连接数最大为500
            config.useSingleServer().setConnectionPoolSize(500);
            //如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
            config.useSingleServer().setIdleConnectionTimeout(10000);
            //同任何节点建立连接时的等待超时。时间单位是毫秒。
            config.useSingleServer().setConnectTimeout(30000);
            //等待节点回复命令的时间。该时间从命令发送成功时开始计时。
            config.useSingleServer().setTimeout(3000);
            config.useSingleServer().setPingTimeout(30000);
            //当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
            config.useSingleServer().setReconnectionTimeout(3000);

            redisson = (Redisson) Redisson.create(config);
            //清空自增的ID数字
            RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
//            atomicLong.set(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用分布式锁
     */
    @Test
    public void testLock() {
        String key = "LockKey";
        RLock mylock = redisson.getLock(key);
        //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        mylock.lock(2, TimeUnit.MINUTES);
        System.err.println("======lock======" + Thread.currentThread().getName());

        try {
            System.out.println("======doSomething======");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName());
    }

    /**
     * 获取原子Id
     */
    @Test
    public void getNextId() {

        RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);

        System.out.println(atomicLong.incrementAndGet());

    }

}
