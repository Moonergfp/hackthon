package com.hack.time.jdk;

import java.text.ParseException;

public class DateTest {
    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
//                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    System.out.println(this.getName()+":"+DateUtil.parse("2013-05-24 06:02:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        for(int i = 0; i < 3; i++){
            new TestSimpleDateFormatThreadSafe().start();
        }

    }
}
