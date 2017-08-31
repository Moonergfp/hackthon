package com.hack.concurrency;

import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class ParallelFrameTest {

    @Test
    public void test() {

        final int rand = new Random(System.currentTimeMillis()).nextInt();
        ParallelFrame parallelFrame = new ParallelFrame(3);


        //1: 定义runner
        Task<String> runner1 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 1 " + rand;
            }
        };
        //2: 定义runner
        Task<String> runner2 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 2 " + rand;
            }
        };
        //2: 定义runner
        Task<String> runner3 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 3 " + rand;
            }
        };
        assertNull(runner1.getResult());
        assertNull(runner2.getResult());
        assertNull(runner3.getResult());
        long currTime = System.currentTimeMillis();
        parallelFrame.execute(runner1);
        parallelFrame.execute(runner2);
        parallelFrame.execute(runner3);

        try {
            System.out.println("try finished....");
            parallelFrame.finished();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finshedTime = System.currentTimeMillis();
        System.out.println("work finished....time cost + " + (finshedTime - currTime));
        assertNotNull(runner1.getResult());
        assertNotNull(runner2.getResult());
        assertNotNull(runner3.getResult());
        System.out.println("result1==>" + runner1.getResult());
        System.out.println("result2==>" + runner2.getResult());
        System.out.println("result3==>" + runner3.getResult());


    }


    /**
     * 测试多个ParallelFrame
     */
    @Test
    public void multiFrame() {
        ParallelFrame parallelFrame1 = new ParallelFrame(2);
        ParallelFrame parallelFrame2 = new ParallelFrame(2);


        final int rand = new Random(System.currentTimeMillis()).nextInt();


        //1: 定义runner
        Task<String> runner1 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 1 " + rand;
            }
        };
        //2: 定义runner
        Task<String> runner2 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 2 " + rand;
            }
        };
        //2: 定义runner
        Task<String> runner3 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 3 " + rand;
            }
        };
        //2: 定义runner
        Task<String> runner4 = new Task<String>() {
            @Override
            public String submit() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello 3 " + rand;
            }
        };
        assertNull(runner1.getResult());
        assertNull(runner2.getResult());
        assertNull(runner3.getResult());
        assertNull(runner4.getResult());
        long currTime = System.currentTimeMillis();
        parallelFrame1.execute(runner1);
        parallelFrame1.execute(runner2);
        parallelFrame2.execute(runner3);
        parallelFrame2.execute(runner4);

        try {
            parallelFrame1.finished();
            parallelFrame2.finished();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finshedTime = System.currentTimeMillis();
        System.out.println("work finished....time cost + " + (finshedTime - currTime));
        assertNotNull(runner1.getResult());
        assertNotNull(runner2.getResult());
        assertNotNull(runner3.getResult());
        assertNotNull(runner4.getResult());


        System.out.println("result1==>" + runner1.getResult());
        System.out.println("result2==>" + runner2.getResult());
        System.out.println("result3==>" + runner3.getResult());
        System.out.println("result4==>" + runner4.getResult());




    }


}
