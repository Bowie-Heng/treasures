package com.bowie.notes.framework.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bowie on 2019/4/16 16:13
 **/
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "你好";
    }
}