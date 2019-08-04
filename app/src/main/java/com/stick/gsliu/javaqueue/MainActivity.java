package com.stick.gsliu.javaqueue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stick.gsliu.javaqueue.Concurrent.ConcurrentUtil;
import com.stick.gsliu.javaqueue.queue.Consumer;
import com.stick.gsliu.javaqueue.queue.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private BlockingQueue<String> queue;
    private Producer producer3;
    private ExecutorService service;
    private Consumer consumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // ConcurrentUtil.testCountDownLatch();
        ConcurrentUtil.testSemaphore();
    }

    private void init() {
        // 声明一个容量为10的缓存队列
        queue = new LinkedBlockingQueue<String>(10);
        service = Executors.newCachedThreadPool();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void startp(View view) {
        producer3 = new Producer(queue);
        service.execute(producer3);
    }

    public void stopp(View view) {
        producer3.stop();
    }

    public void startc(View view) {
        consumer = new Consumer(queue);
        service.execute(consumer);
    }

    public void stopc(View view) {
        consumer.stop();
    }
}
