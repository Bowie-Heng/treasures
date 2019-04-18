package com.bowie.notes.framework.web;

import com.bowie.notes.framework.common.Result;
import com.bowie.notes.framework.entity.Order;
import com.bowie.notes.framework.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Bowie on 2019/4/17 16:36
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

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

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public String select(@RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "size", required = false) Integer pageSize, Model model) {

        List<Order> orderList = orderService.selectByPageHelper(page, pageSize);

        model.addAttribute("orders", orderList);

        return "order/list";
    }


}
