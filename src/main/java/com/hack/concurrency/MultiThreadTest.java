package com.hack.concurrency;

import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * 多线程测试
 */
public class MultiThreadTest {

    /**
     * 无限制创建线程
     */
    @Test
    public void noLimitTest() {
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        System.out.println(Thread.currentThread().getName() + " j= " + j);
                    }
                }
            }).start();
        }
    }


    public void testExecutor(){
        Executors.newFixedThreadPool(2);
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(2);
    }

}
