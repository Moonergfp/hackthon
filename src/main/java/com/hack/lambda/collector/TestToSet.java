package com.hack.lambda.collector;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestToSet {


   @Test
   public void testToSet(){
      List<Integer> ls = Arrays.asList(1,2,3,1,22,55,3,12);
      System.out.println("ls===>"+ls);

     Set<Integer> ss =ls.stream().collect(Collectors.toSet()) ;
      System.out.println("ss===>"+ss);


       System.out.println("sortetd===>"+ls.stream().distinct().sorted(Integer::compare).collect(Collectors.toList()));

       Comparator<Integer> c = Integer::compare;
       System.out.println("sortetd===>"+ls.stream().distinct().sorted(c.reversed()).collect(Collectors.toList()));


   }


}
