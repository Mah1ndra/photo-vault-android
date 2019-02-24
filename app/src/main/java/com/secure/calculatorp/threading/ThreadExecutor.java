package com.secure.calculatorp.threading;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;



public class ThreadExecutor {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1000;
    private static final int TIMEOUT = 60;
    private static final TimeUnit TIMEOUT_TIME_UNIT = TimeUnit.SECONDS;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>();
    private static final String THREAD_EXECUTOR = "THREAD_EXECUTOR";


    private ThreadPoolExecutor threadPoolExecutor;

    @Inject
    public ThreadExecutor() {

    }

    public void createPool() {
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingDeque<>());
    }

    public Future<?> execute(Runnable runnable) {
        return threadPoolExecutor.submit(runnable);
    }

    public void shutdown(){
        threadPoolExecutor.shutdown();
    }

    public void awaitTermination(){
        try {
            while (!threadPoolExecutor.awaitTermination(TIMEOUT, TIMEOUT_TIME_UNIT)) {
                Log.i(THREAD_EXECUTOR,"Awaiting completion of threads.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
