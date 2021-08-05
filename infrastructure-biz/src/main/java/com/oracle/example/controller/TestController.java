package com.oracle.example.controller;


import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/demo")
@PropertySource("classpath:chuanglan-application.properties")
public class TestController {


    @ResponseBody
    @RequestMapping("/query")
    public Integer query(){
        return 1;
    }


}
