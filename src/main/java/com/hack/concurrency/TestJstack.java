package com.hack.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试Jstack命令
 */
public class TestJstack {
    private static User obj = new User();


    private static ThreadPoolExecutor exec = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        Task t1 = new Task();
        Task t2 = new Task();
        exec.execute(t1);
        exec.execute(t2);
        exec.shutdown();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            synchronized (obj) {
//                cal();
                try {
                    System.out.println(Thread.currentThread().getName() + "-->cal--->");
                   if(obj.getNum() == 0){
                       System.out.println("waiting ---");
                      obj.setNum(1);
                       obj.wait();
                       System.out.println(Thread.currentThread().getName()  +"waiting finished");
                   }else if (obj.getNum() == 1){
                       System.out.println("notify---");
                      obj.notifyAll();
                   }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()  +" threadfinished");
        }

        public void cal() {
            int i = 0;
            while (true) {
                i++;
            }
        }
    }

    static class  User {
       private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
