package com.hack.spring;

import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class TestXmlBeanFactory {


   @Test
   public void testInitIoc(){
      ClassPathResource cpr = new ClassPathResource("applicationContext.xml");
      XmlBeanFactory beanFactory = new XmlBeanFactory(cpr);



      Object obj= beanFactory.getBean("hello");
      System.out.println(obj);


   }

   @Test
   public void testInitIoc2(){
      ClassPathResource cpr = new ClassPathResource("applicationContext.xml");

      DefaultListableBeanFactory dlb = new DefaultListableBeanFactory();
      XmlBeanDefinitionReader xbdr = new XmlBeanDefinitionReader(dlb);
      xbdr.loadBeanDefinitions(cpr);


      Object obj= dlb.getBean("hello");
      System.out.println(obj);
   }

}
