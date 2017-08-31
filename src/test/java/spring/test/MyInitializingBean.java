package spring.test;

import org.springframework.beans.factory.InitializingBean;

public class MyInitializingBean {
//public class MyInitializingBean implements InitializingBean {

   public void myInitMethod(){
       System.out.println("myInitMethod");
   }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet");
//    }
}
