package com.hack.time.jdk;

import org.junit.Test;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

public class LocalDateTest {

    @Test
    public void SystemZoneTest() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        System.out.println(now.get(ChronoField.NANO_OF_DAY));
    }


    @Test
    public void dayOfWeekInMonthTest(){
       LocalDate now = LocalDate.now();
        System.out.println(now);

        System.out.println(now.with(TemporalAdjusters.dayOfWeekInMonth(-2, DayOfWeek.TUESDAY)));

        Temporal temp = now.with(DAY_OF_MONTH, now.range(DAY_OF_MONTH).getMaximum());
        System.out.println("temp===>"+temp);

    }

    @Test
    public void range(){
        LocalDate now = LocalDate.now();
        for(int i = 1 ; i <=10 ;i++){

            System.out.println(now.range(DAY_OF_MONTH).getMinimum());
        }

    }

    @Test
    public void formatTest(){
        LocalDate now = LocalDate.now();
        String s1 = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(s1);
    }


    @Test
    public void ajuster(){
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1L);

        System.out.println(now);
        System.out.println(yesterday);


        System.out.println(now.with(yesterday));

        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println(nowTime.with(TemporalAdjusters.firstDayOfMonth()));

        System.out.println(now.format(DateTimeFormatter.ISO_DATE_TIME));

    }




}
