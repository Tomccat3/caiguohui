package com.myapp.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cgh
 *
 */
public class SingletonPool {

    /**
     *  类初始化的方式实现单例线程池
     * @param
     * @return
    */
    private static final AtomicInteger count = new AtomicInteger(1);

    private static class InsatanceHolder{
        public static ExecutorService service = Executors.newFixedThreadPool(6);
    }

    public static ExecutorService getInstance(){
        return InsatanceHolder.service;
    }

    public static AtomicInteger getCount(){
        return count;
    }
}
