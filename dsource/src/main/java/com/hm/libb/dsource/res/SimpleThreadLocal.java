package com.hm.libb.dsource.res;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangming on 2017/6/22.
 */
public class SimpleThreadLocal {

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    private SimpleThreadLocal() {

    }

    public static void execute(Runnable t) {
        fixedThreadPool.execute(t);
    }
}
