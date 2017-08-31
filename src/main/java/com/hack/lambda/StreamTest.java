package com.hack.lambda;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void testForeach() {
        List<String> ls = Arrays.asList("a", "b", "asf", "helloworld");

        ls.stream().forEach(s -> System.out.println(s));

    }

    @Test
    public void testOperator() {
        List<String> ls = Arrays.asList("a", "b2", "asf", "helloworld","hello");
        List<Integer> lengthLs = ls.stream().sorted((s1,s2)->s1.length()-s2.length()).filter(
                s -> {
                    System.out.println("filter-->" + s);
                    return s.length() > 1;
                }).map(s -> {
                    System.out.println("map ,s.length--->" + s.length());
                    return s.length();
                }
        ).limit(3).collect(Collectors.toList());
        System.out.println(lengthLs);

        System.out.println("#########");
        System.out.println(ls);
        System.out.println("#########");



        List<Integer> lengthLs2 = ls.stream().filter(
                s -> {
                    System.out.println("filter-->" + s);
                    return s.length() > 1;
                }).sorted((s1,s2)->s1.length()-s2.length()).map(s -> {
                    System.out.println("map ,s.length--->" + s.length());
                    return s.length();
                }
        ).limit(3).collect(Collectors.toList());
        System.out.println(lengthLs2);
    }


    @Test
    public void testFilter(){
        List<String> ls = Lists.newArrayList();
        ls.add("a");
        ls.add("aaf");
        ls.add("ahello");
        ls.add("aworld");
        ls.add("hh");
        List<String> ls2 = ls.stream().filter(s->s.length()>2).collect(Collectors.toList());

        System.out.println(ls);
        System.out.println(ls2);
        ls.add("nihaome");
        System.out.println(ls2);




    }


    @Test
    public void createStream(){
        Stream.iterate(0,n->n+2).limit(10).forEach(System.out::println);
        Stream.generate(Math::random).limit(10).forEach(System.out::println);


        Stream<String> s1 = Stream.of("hello","world","nihao");
        Stream<String> s2 = Arrays.stream(new String[]{"hello","world","nihao"});
        Stream<String> s3 = Stream.empty();
    }


}
