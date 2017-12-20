package com.stick.gsliu.javaqueue.queue;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liugstick on 2017/12/20.
 */

public class Producer implements Runnable {

    private static final String TAG = "Producer";

    private BlockingQueue<String> queue;
    private boolean isRunning;
    private Random r = new Random();
    private AtomicInteger count = new AtomicInteger();

    public Producer(BlockingQueue<String> deque) {
        this.queue = deque;
    }

    @Override
    public void run() {
        Log.i(TAG, "启动生产者线程");
        String data;
        isRunning = true;

        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(1000));
                data = "data:" + count.incrementAndGet();

                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    Log.i(TAG, "放入数据失败：" + data);
                } else {
                    Log.i(TAG, "放入数据成功：" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            Log.i(TAG, "生产者线程退出！");
        }

    }

    public void stop() {
        this.isRunning = false;
    }
}
