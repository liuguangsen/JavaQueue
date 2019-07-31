package com.stick.gsliu.javaqueue.Concurrent;

import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class ConcurrentUtil {
    private static final String TAG = "ConcurrentUtil";
    private static final CountDownLatch end = new CountDownLatch(10);

    public static void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG,"线程1阻塞");
                    end.await();
                    Log.d(TAG,"线程1执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        jian();
    }

    private static void jian() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"线程2执行");
                for (int i = 0; i < 10; i++) {
                    end.countDown();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"线程2结束");
            }
        }).start();
    }

}
