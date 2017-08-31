package com.hack.optional;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OptionalTest {

    @Test
    public void test() {

        MyEntity e1 = new MyEntity();
        MyEntity e2 = new MyEntity();

        e1.setName("张三");
        e1.setAge(10);

        e2.setName("李四");
        e2.setAge(8);

        List<MyEntity> ls = Lists.newArrayList(e1, e2);

        List<String> names = ls.stream().map(e -> e.getName()).collect(Collectors.toList());

        names.stream().forEach(System.out::println);

        List<String> ls2 = Lists.newArrayList();

        String str2 = ls2.stream().findAny().orElse(null);

        System.out.println(str2);

    }

    @Test
    public void PecsTest() {

        MyEntity e1 = new MyEntity();
        MyEntity e2 = new MyEntity();

        e1.setName("张三");
        e1.setAge(10);

        e2.setName("李四");
        e2.setAge(8);
        List<? extends MyEntity> ls = Lists.newArrayList(e1, e2);
        ls.stream().forEach(System.out::println);





    }

}
