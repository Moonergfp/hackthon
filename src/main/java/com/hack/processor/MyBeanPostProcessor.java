package com.hack.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanPostProcessor.class);
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("#MyBeanPostProcessor.postProcessBeforeInitialization beanName={},toString={}#",beanName,bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("#MyBeanPostProcessor.postProcessAfterInitialization beanName={},toString={}#",beanName,bean);
        return bean;
    }
}
