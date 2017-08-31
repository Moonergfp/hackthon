package com.hack.time.jdk;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

public class DateTest2 {


   @Test
   public void test(){
       DateTime dateTime = DateTime.now();
      int startUnixTime =
              date2Unixtime(dateTime.minusDays(8).toDate());
       System.out.println(startUnixTime);
   }

    private int date2Unixtime(Date date) {
        return (int) (date.getTime() / 1000L);
    }
}
