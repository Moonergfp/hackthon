package com.hack.lambda;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

/**
 * 测试foreach中断
 */
public class ForEachTest {

    @Test
    public void test() {
        List<String> ls = Arrays.asList("a", "b", "exception", "c", "hello");
        try {
            ls.stream().forEach(ForEachTest::print);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(String val) {
        if ("exception".equals(val)) {
            throw new RuntimeException("exception");
        }
        System.out.println(val);
    }

}
