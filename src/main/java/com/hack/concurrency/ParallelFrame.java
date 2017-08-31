package com.hack.concurrency;

import java.util.concurrent.*;

/**
 * 并行框架
 */
public class ParallelFrame<T> {

    private  CountDownLatch endGate;
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public ParallelFrame(int n) {
        endGate = new CountDownLatch(n); //初始化闭锁
    }

    public void execute(Task r) {
        try {
            r.setParallelFrame(this);
            executor.submit(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch getEndGate() {
        return endGate;
    }


    public void finished() throws InterruptedException {
       endGate.await();
    }
}

abstract class Task<V> implements Runnable {
    private ParallelFrame parallelFrame;
    private V result;

    public void run() {
        setResult(submit());
        parallelFrame.getEndGate().countDown();
    }

    public abstract V submit();

    public ParallelFrame getParallelFrame() {
        return parallelFrame;
    }

    public V getResult() {
        return result;
    }

    private void setResult(V result) {
        this.result = result;
    }

    public void setParallelFrame(ParallelFrame parallelFrame) {
        this.parallelFrame = parallelFrame;
    }
}



