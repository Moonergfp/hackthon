package com.hack.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.hack.conf.PropertyUtil;
import com.hack.dao.UserDao;
import com.hack.domain.UserDb;
import com.hack.service.LogService;
import com.hack.util.HttpUtil;
import com.hack.util.JSONUtil;
import com.hack.util.LogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@RequestMapping("/log")
public class LogController {
    private static Logger logger = LoggerFactory.getLogger(LogController.class);
    @Resource
    private LogService logService;


    @ResponseBody
    @RequestMapping("/test")
    public Object test() {
        logger.info("#Longcontroller test#");
        logger.debug("#Longcontroller test debug#");
        LogConstant.runLog.debug("#Longcontroller run log test debug#");
        logService.test();
        return null;
    }

}
