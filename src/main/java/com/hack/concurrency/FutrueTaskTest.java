package com.hack.concurrency;


import org.junit.Test;

import java.util.concurrent.*;

public class FutrueTaskTest {
    /**
     * FutureTask也可以用来做闭锁
     * 测试闭锁
     */
    @Test
    public void testLatch() throws ExecutionException, InterruptedException {

        Callable<String>  c = new Callable<String> () {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);  // 长时间执行的任务
                System.out.println("other thread");
                return "success";
            }
        };

        ExecutorService es =  Executors.newCachedThreadPool();
        Future f = es.submit(c);

//        Future f = es.submit(futrueTask);
//            System.out.println("main");
        System.out.println("f===>"  + f.get());
//            System.out.println("f===>"  + futrueTask.get());
        }


        private FutureTask<String> futrueTask = new FutureTask(new Callable<String> () {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);  // 长时间执行的任务
                return "success";
        }
    });


    @Test
    public void testMap(){
        ConcurrentHashMap<Integer,String> ch = new ConcurrentHashMap();
       String v = ch.putIfAbsent(1,"abc") ;
        String v2=  ch.putIfAbsent(1,"abc") ;
        System.out.println(v);
        System.out.println(v2);



    }

}
