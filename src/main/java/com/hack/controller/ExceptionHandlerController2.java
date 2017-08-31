package com.hack.controller;

import com.hack.util.LogConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/exp")
public class ExceptionHandlerController2 {


    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public String testException(RuntimeException re) {
        LogConstant.runLog.warn("====testException====", re);
        return "exception";
    }

//
//    @ResponseBody
//    @RequestMapping("/test")
//    public Object test(String abc) {
//
//        LogConstant.runLog.info("====test====");
//        System.out.println(abc.toString());
//        return "test";
//    }


}
