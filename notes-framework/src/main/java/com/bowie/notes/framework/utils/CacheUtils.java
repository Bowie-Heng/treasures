package com.bowie.notes.framework.utils;

import com.bowie.notes.framework.dao.OrderMapper;
import com.bowie.notes.framework.entity.Order;
import com.bowie.notes.framework.entity.OrderExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bowie on 2019/5/10 11:52
 **/
@Component
public class CacheUtils {

    private final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    public static final String redisKeyPre = "OrderId_";

    public static final String ehcacheKeyPre = "OrderId_";

    public static final String cacheName = "nativeCache";

    //容器启动的时候预热数据
    @PostConstruct
    public void preheat() {
        List<Order> orders = orderMapper.selectByExample(new OrderExample());
        for (Order order : orders) {
            redisTemplate.opsForValue().set(redisKeyPre + String.valueOf(order.getId()), order, 60, TimeUnit.SECONDS);
            getNativeCache().putIfAbsent(ehcacheKeyPre + order.getId(), order);
        }

    }


    public Order getValue(String orderId) {
        Object o;
        try {
            o = redisTemplate.opsForValue().get(redisKeyPre + orderId);
        } catch (Exception e) {
            //这里如果redis出现异常，从redis中获取不到的话，自动降级
            //修改为从本地缓存中获取内容
            logger.error("从redis中获取数据失败，orderId = " + orderId, e);
            o = getNativeCache().get(ehcacheKeyPre + orderId).get();
        }
        if (o == null) {
            //如果获取的时候为null，尝试从数据库中获取，然后再加入到缓存中去
            Order order = orderMapper.selectByPrimaryKey(Long.valueOf(orderId));
            //这里，不管获取到内容没有，都存到缓存中，防止缓存穿透
            try {
                redisTemplate.opsForValue().set(redisKeyPre + orderId, order, 10, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("往redis中存放数据失败，orderId = " + orderId, e);
            }
            try {
                getNativeCache().putIfAbsent(ehcacheKeyPre + orderId, order);
            } catch (Exception e) {
                logger.error("往本地缓存中存放数据失败，orderId = " + orderId, e);
            }
        }
        return o == null ? null : (Order) o;
    }

    private Cache getNativeCache() {
        return ehCacheCacheManager.getCache(cacheName);
    }
}
