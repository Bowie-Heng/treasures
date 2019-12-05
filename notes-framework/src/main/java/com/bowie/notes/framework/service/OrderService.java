package com.bowie.notes.framework.service;

import com.bowie.notes.framework.entity.Order;

import java.util.List;

/**
 * Created by Bowie on 2019/4/17 16:40
 **/
public interface OrderService {
    void insert();

    List<Order> selectByPageHelper(Integer page, Integer pageSize);

    Order selectById(String orderId);

    Order selectByIdUsingCache(String id);

    void sendToQueue();

}
