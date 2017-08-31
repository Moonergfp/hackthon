package com.hack.classloader.controller;


import com.hack.classloader.hierarchy.ClassLoaderTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/web")
@Controller
public class WebContainerController {

    @RequestMapping("/printClassLoader")
    @ResponseBody
    public Object printClassLoader(){
        ClassLoader loader = this.getClass().getClassLoader(); // 获得加载ClassLoaderTest.class这个类的类加载器
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent(); // 获得父类加载器的引用
        }
        System.out.println(loader);
        return null;
    }


}
