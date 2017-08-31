package com.hack.concurrency;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;


/**
 * 测试线程的中断、取消操作
 */
public class ThreadCancelTest {


    /**
     * 测试简单的取消策略
     */
    @Test
    public void TestSimpleCancelPolicy() {
        SimpleCancel sc = new SimpleCancel();
        Thread t = new Thread(sc);
        t.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            sc.cancel();
        }

    }

    class SimpleCancel implements Runnable {

        private boolean cancel = false;

        @Override
        public void run() {
            while (!cancel) {
                System.out.printf(Thread.currentThread().getName() + "running ");
            }
            System.out.println("finished do something");
        }

        public void cancel() {
            cancel = true;
        }

    }


    @Test
    public void TestSimpleCancelPolicy2() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               while(true) {
//                   try {
//                       Thread.currentThread().sleep(100);
//                   } catch (InterruptedException e) {
//                       e.printStackTrace();
//                   }
                   System.out.println("hello world");
               }
            }
        });
        t.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
           t.interrupt();
            System.out.println(t.isInterrupted() + " interrupt succeed");
        }

    }

}
