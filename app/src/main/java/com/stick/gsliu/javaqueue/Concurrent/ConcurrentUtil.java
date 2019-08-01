package com.stick.gsliu.javaqueue.Concurrent;

import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

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
}
