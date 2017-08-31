package com.hack.time.jdk;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class DurationTest {

    @Test
    public void addOneHour() {
        Duration sixtyMins = Duration.of(60, ChronoUnit.MINUTES);
        Duration oneHour= Duration.of(1, ChronoUnit.HOURS);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(sixtyMins.addTo(now));
        System.out.println(oneHour.addTo(now));

        //输出秒数
        System.out.println(sixtyMins.get(ChronoUnit.SECONDS));
        System.out.println(sixtyMins.get(ChronoUnit.MINUTES));
        System.out.println(sixtyMins.get(ChronoUnit.HOURS));
    }


}
