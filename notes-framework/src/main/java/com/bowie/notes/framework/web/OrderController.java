package com.bowie.notes.framework.web;

import com.bowie.notes.framework.common.Result;
import com.bowie.notes.framework.entity.Order;
import com.bowie.notes.framework.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Bowie on 2019/4/17 16:36
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 这个url实现了分库分表的插入
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> insert() {

        orderService.insert();

        Result<String> result = new Result<String>();

        result.setCode(200);
        result.setSuccess(true);
        result.setData("成功啦!");
        return result;
    }

    /**
     * 这个url实现了分库分表的查询
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select(@RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "size", required = false) Integer pageSize, Model model) {

        List<Order> orderList = orderService.selectByPageHelper(page, pageSize);

        model.addAttribute("orders", orderList);

        return "order/list";
    }

    /**
     * 这里进行了一系列的措施来防止缓存雪崩和缓存穿透
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public String selectSingleOrder(@PathVariable String id, Model model) {

        Order order = orderService.selectById(id);

        model.addAttribute("order", order);

        return "order/orderDetails";
    }


    /**
     * 这里使用Spring的默认缓存机制
     * @return
     */
    @RequestMapping(value = "/select/cache/{id}", method = RequestMethod.GET)
    public String selectByCache(@PathVariable String id, Model model) {

        Order order = orderService.selectByIdUsingCache(id);

        model.addAttribute("order", order);

        return "order/orderDetails";
    }



}
