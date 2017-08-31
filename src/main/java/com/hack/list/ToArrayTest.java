package com.hack.list;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ToArrayTest {


    @Test
    public void toArrayTest(){
        List<String> ls = Lists.newArrayList();
        ls.add("hello") ;
        ls.add("nihao") ;

        String[] strings = new String[1];

        String[] strs = ls.toArray(strings);
        for(String str: strs){
            System.out.println(str);
        }



    }


    /**
     *
     */
    @Test
    public void toArrayTest2(){
        List<String> ls = Lists.newArrayList();
        ls.add("hello") ;
        ls.add("nihao") ;
        ls.add(null) ;

        String[] strings = new String[4];

        String[] strs = ls.toArray(strings);
        for(String str: strs){
            System.out.println(str);
        }



    }

    @Test
    public void removeTEst(){

        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("2".equals(temp)) {
                a.remove(temp);
            }
        }
        System.out.println(a);
    }



}
