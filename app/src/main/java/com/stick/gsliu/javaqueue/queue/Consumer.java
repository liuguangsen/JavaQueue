package com.stick.gsliu.javaqueue.queue;

import android.util.Log;

import java.util.Random;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by liugstick on 2017/12/20.
 */

public class Consumer implements Runnable {

    private static final String TAG = "Producer1";

    private BlockingQueue<String> deque;
    private boolean isRunning;
    private Random r = new Random();

    public Consumer(BlockingQueue<String> deque) {
        this.deque = deque;
    }

    @Override
    public void run() {
        Log.i(TAG, "启动消费者线程");
        isRunning = true;

        try {
            while (isRunning) {
                String data = deque.poll(5, TimeUnit.SECONDS);
                if (null != data) {
                    Log.i(TAG, " 消费者拿去一个数据： " + data);
                    Thread.sleep(r.nextInt(1000));
                } else {
                    //超过5秒还没拿到数据，认为生产者线程都已经退出
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            Log.i(TAG, "消费者线程退出！");
        }

    }

    public void stop() {
        this.isRunning = false;
    }
}
