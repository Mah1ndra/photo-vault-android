package com.secure.calculatorp;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
//        Runtime.getRuntime().availableProcessors(),    // Initial pool size
//                Runtime.getRuntime().availableProcessors(), // Max pool size
//        1000,  // Time idle thread waits before terminating
//        TimeUnit.MILLISECONDS ,       // Sets the Time Unit for keepAliveTime
//        new LinkedBlockingDeque<>());


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.print("Hello World\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 10 ; i++) {
            threadPoolExecutor.execute(runnable);
        }
        threadPoolExecutor.shutdown();
        while (!threadPoolExecutor.awaitTermination(2, TimeUnit.MINUTES)){

        }
        System.out.print("dsds");
    }
}