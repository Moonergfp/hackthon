package com.hack.reflection;

import org.junit.*;

public class ClassTest {

    class My {
        public void test1() {
            System.out.println("test1 method");
        }

//        @Deprecated
//        public void test2() {
//            System.out.println("test2 method");
//        }
    }


    /**
     * 测试Deprecated注解
     */
    @org.junit.Test
   public void test(){

        My my = new My();

//        my.test2();
        my.test1();

   }



    public static void main(String[] args) {





        try {
            System.out.println("tesetss");
            Class c = Class.forName(args[0]);
            Package p = c.getPackage();
            System.out.println(p.getName());

            int num = c.getModifiers();
            System.out.println("num==>" + num);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("沒有指定類別");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定類別");
        }
    }
}
