package com.hack.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 闭锁测试
 */
public class CountDownLatchTest {
    private Random random = new Random();

    public void printNum() {
        for (int i = 0; i < random.nextInt(10); i++) {
            System.out.println(Thread.currentThread().getName() + " i =" + i);
        }
    }

    public void run(int n) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(n);

        for (int i = 0; i < n; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startGate.await();  //等待所有线程初始化完毕
                        printNum();  //执行业务逻辑
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();  //完成一个,endGate递减
                    }
                }
            });
            t.start();
        }
        long sTime = System.nanoTime();
        //所有 线程就绪,开放所以线程
        startGate.countDown();
        try {
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long eTime = System.nanoTime();

        System.out.println("=========共耗时" + (eTime-sTime));

    }

    public static void main(String[] args){
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        countDownLatchTest.run(10);
    }
}
