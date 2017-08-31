package com.hack.time.jdk;

import org.junit.Test;

import java.time.Clock;

public class ClockTest {


    @Test
    public void SystemZoneTest(){
        System.out.println(Clock.systemDefaultZone());
    }

}
