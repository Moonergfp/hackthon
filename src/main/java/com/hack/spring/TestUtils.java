package com.hack.spring;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class TestUtils {


   @Test
   public void testStringUtils(){

     String myStr  = "guofeipeng;nihao,women,;hh";

       String[] arr = StringUtils.tokenizeToStringArray(myStr,";,");
       for(String s : arr){
           System.out.println(s);
       }

   }


}
