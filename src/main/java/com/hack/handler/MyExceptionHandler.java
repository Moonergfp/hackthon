package com.hack.handler;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.google.common.collect.Maps;
import com.hack.util.LogConstant;
import com.sun.javafx.binding.LongConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class MyExceptionHandler implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LogConstant.runLog.info("====MyExceptionHandler=====");
        //如果要返回json,可以使用如下办法返回,否则可以直接返回视图
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        mv.setView(view);
        Map<String,Object> map = Maps.newHashMap();
        map.put("test","test");
        mv.addAllObjects(map);
        return mv;
    }
}
