package com.hack.time.jdk;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;

public class InstantTest {

    @Test
    public void testGetDay() {
        System.out.println(Instant.now().get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    public void nowTest() {
        System.out.println(Instant.now());
    }

    @Test
    public void durationTEst() {
        System.out.println(Duration.between(Instant.ofEpochSecond(1473841187000L), Instant.ofEpochMilli(1474185899000L)));
        System.out.println(Duration.between(Instant.ofEpochSecond(1473841187000L), Instant.ofEpochMilli(1474185899000L)).isNegative());
        System.out.println( Duration.between(Instant.ofEpochMilli(1474185899000L),Instant.ofEpochSecond(1473841187000L)).isNegative());



    }

}
