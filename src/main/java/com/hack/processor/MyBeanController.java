package com.hack.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/hack/bean")
public class MyBeanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanController.class);
    @Resource
    private MyBean myBean;

    @RequestMapping("/getBean")
    public Object getMyBean() {
        LOGGER.info("myBean tostring-->{}", myBean);
        return myBean;

    }

}
