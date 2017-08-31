package com.hack.test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hack.util.HttpUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    private static final SimpleDateFormat SELECTION_OPT_DATE_FORMAT = new SimpleDateFormat("yyyy/M/d");
    private static final SimpleDateFormat SELECTION_OPT_WMS_PARAMS_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static final String YES = "YES";

    @org.junit.Test
    public void testYes() {
        System.out.println(Test.YES.equals(HttpUtil.YES));
    }

    @org.junit.Test
    public void test() throws ParseException {
        String timeSpanStr = "2017/1/7-2017/1/13";

        List<String> timeSpans = Splitter.on('-').omitEmptyStrings().splitToList(timeSpanStr);

        Date startDate = SELECTION_OPT_DATE_FORMAT.parse(timeSpans.get(0));
        Date endDate = SELECTION_OPT_DATE_FORMAT.parse(timeSpans.get(1));
        String startTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(startDate);
        String endTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(endDate);

        Calendar lastStartCal = Calendar.getInstance();
        Calendar lastEndCal = Calendar.getInstance();
        lastStartCal.setTime(startDate);
        lastEndCal.setTime(endDate);
        lastStartCal.add(Calendar.DATE, -7);
        lastEndCal.add(Calendar.DATE, -7);

        String lastStartTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(lastStartCal.getTime());
        String lastEndTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(lastEndCal.getTime());

        System.out.println(startTimeStr);
        System.out.println(endTimeStr);
        System.out.println(lastStartTimeStr);
        System.out.println(lastEndTimeStr);
    }

    /**
     * 测试Integer decode方法
     */
    @org.junit.Test
    public void testDecode() {

        Integer i = 355;
        Integer l = 355;
        System.out.println(i.equals(l));

        MyDto myDto = new MyDto();

        myDto.setName("guofeipeng");
        System.out.println(myDto);
    }

    enum TTT {
        HELLO;
    }

    static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    @org.junit.Test
    public void testSort() {
        Person[] people = new Person[] { new Person("a", 12), new Person("b", 11), new Person("c", 22) };
        Comparator<Person> byName = Comparator.comparing(p -> p.getAge());
        Arrays.sort(people, byName);
        for (Person p : people) {
            System.out.printf(p.getName() + " " + p.getAge());

        }
    }

    @org.junit.Test
    public void testDouble() {
        double db = -Double.valueOf(5);
        System.out.println(db);
    }


    @org.junit.Test
    public void testtt() {
        List<String> ls = Lists.newArrayList();
        ls.add("a");
        ls.add("b");


        for(int i = 1 ; i < ls.size()-1; i++){
            System.out.println(ls.get(i));
        }

    }




}
