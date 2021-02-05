package com.haoniu.quchat.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lhb
 * @date 2019/2/13
 * 线程池（线程管理类）
 */

public class ThreadPoolManager {
    private static ThreadPoolManager mInstance;

    public static ThreadPoolManager getInstance() {
        if (null == mInstance) {
            synchronized (ThreadPoolManager.class) {
                if (null == mInstance) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 核心线程池数量，同时能够执行的线程数量
     */
    private int corePoolSize;

    /**
     * 最大线程池数量，表示当缓冲队列满的时候能够容纳的等待任务的数量
     */
    private int maximumPoolSize;

    /**
     * 存活时间
     */
    private long keepAliveTime = 1;
    private TimeUnit unit = TimeUnit.HOURS;
    private ThreadPoolExecutor executor;

    private ThreadPoolManager() {
        //给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //虽然maximumPoolSize用不到，但是需要赋值，否则报错
        maximumPoolSize = corePoolSize;

        executor = new ThreadPoolExecutor(
                //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                corePoolSize,
                //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                maximumPoolSize,
                //表示的是maximumPoolSize当中等待任务的存活时间
                keepAliveTime,
                unit,
                //缓冲队列，用于存放等待任务，Linked的先进先出
                new LinkedBlockingDeque<Runnable>(),
                //创建线程的工厂
//                Executors.defaultThreadFactory(),
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "def-pool-"),
                //用来对超出maximumPoolSize的任务的处理策略
                new ThreadPoolExecutor.AbortPolicy());

    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        if (null == executor) {
            //线程池执行者。
            //参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
            executor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
//                       Executors.defaultThreadFactory(),
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, "def-pool-"),
                    new ThreadPoolExecutor.AbortPolicy());
        }

        if (null != runnable) {
            executor.execute(runnable);
        }
    }

    /**
     * 移除任务
     */
    public void remove(Runnable runnable) {
        if (null != runnable) {
            executor.remove(runnable);
        }
    }

    /**
     * 创建线程池的工厂，设置线程的优先级，group,以及命名
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程池的计数
         */
        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        /**
         * 线程的计数
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriorty, String threadNamePrefix) {
            //设置线程的优先级，group,以及命名
            this.threadPriority = threadPriorty;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }

            t.setPriority(threadPriority);
            return t;
        }
    }
}
