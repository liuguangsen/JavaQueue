package com.stick.gsliu.javaqueue.Concurrent;

import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ConcurrentUtil {
    // 测试 CountDownLatch api
    private static final String TAG = "ConcurrentUtil";
    private static final CountDownLatch end = new CountDownLatch(10);

    public static void testCountDownLatch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "线程1阻塞");
                    end.await();
                    Log.d(TAG, "线程1执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        minus();
    }

    private static void minus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "线程2执行");
                for (int i = 0; i < 10; i++) {
                    end.countDown();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "线程2结束");
            }
        }).start();
    }

    // 测试 CountDownLatch api

    private static final Runnable r = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "队长 安排吃饭！");
        }
    };
    private static CyclicBarrier barrier;

    public static void testCyclicBarrier() {
        barrier = new CyclicBarrier(10, r);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d(TAG, "士兵 " + (finalI + 1) + " 到达");
                        barrier.await();
                        Log.d(TAG, "士兵 " + (finalI + 1) + " 吃饭！");
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Semaphore 控制允许的线程数目，独立的线程调用 acquire 获取一个(Semaphore 增加一个)，使用完毕 需要调用release（Semaphore 释放一个）
     * 简单来说就是Semaphore 如果数目已经满了， 那么独立线程调用 acquire 会阻塞，除非加一个超时
     */
    public static void testSemaphore() {
        final Semaphore semaphore = new Semaphore(2);
        for (int i = 0;i < 1;i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d(TAG, "士兵 " + (finalI + 1) + " 等待！" + semaphore.getQueueLength());
                        semaphore.acquire();
                        Log.d(TAG, "士兵 " + (finalI + 1) + " 吃饭！" + semaphore.getQueueLength());
                        Thread.sleep(5000);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
