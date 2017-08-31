package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.UserDao;
import com.hack.domain.UserDb;
import com.hack.util.HttpUtil;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@RequestMapping("/exp")
public class ExceptionHandlerController {

//
//    @ExceptionHandler({RuntimeException.class})
//    @ResponseBody
//    public String testException(RuntimeException re) {
//        LogConstant.runLog.warn("====testException====", re);
//        return "exception";
//    }


    @ResponseBody
    @RequestMapping("/test")
    public Object test(String abc) {

        LogConstant.runLog.info("====test====");
        System.out.println(abc.toString());
        return "test";
    }


}
