package com.hack.lambda;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * 测试局部变量
 */
public class TestLocalVar {

    @Test
    public void test(){
        final String v = "1.8";   //局部变量必须声明final,或者可以不声明final,但是后面不能使用,相当于lambda强制给加了final
        Consumer<String>  c = str -> System.out.println(v);
        consumer("helloworld",c);
    }
    public static void consumer(String str ,Consumer<String> c){
       c.accept(str);
    }

}

