package com.bowie.notes.framework.service.impl;

import com.bowie.notes.framework.dao.OrderMapper;
import com.bowie.notes.framework.entity.Order;
import com.bowie.notes.framework.entity.OrderExample;
import com.bowie.notes.framework.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bowie on 2019/4/17 16:40
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public void insert() {

        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setNickName("NickName_" + i);
            order.setOrderId("OrderId_" + i);
            order.setUserId("UserId_" + i);
            order.setUsername("UserName_" + i);
            order.setUserSex("女");
            order.setPassword(getPassWord());
            orderMapper.insertSelective(order);
        }

    }

    public List<Order> selectByPageHelper(Integer page, Integer pageSize) {

        //PageHelper首先将前端传递的参数保存到page这个对象中，接着将page的副本存放入ThreadLoacl中，这样可以保证分页的时候，参数互不影响，
        // 接着利用了mybatis提供的拦截器，取得ThreadLocal的值，重新拼装分页SQL，完成分页。
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        } else {
            PageHelper.startPage(0, 10);
        }
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("order_id desc");

        List<Order> orders = orderMapper.selectByExample(orderExample);

        PageInfo<Order> orderPageInfo = new PageInfo<Order>(orders);

        return orderPageInfo.getList();
    }

    private String getPassWord() {
        try {
            UUID uuid = UUID.randomUUID();
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(uuid.toString().getBytes());
            //获得加密后的数据
            //将加密后的数据转换为16进制数字
            String md5code = new BigInteger(1, md.digest()).toString(16);// 16进制数字
            // 如果生成数字未满32位，需要前面补0
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
            return md5code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "出错了的密码";
    }
}
