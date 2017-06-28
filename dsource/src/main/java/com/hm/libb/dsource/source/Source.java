package com.hm.libb.dsource.source;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by huangming on 2017/6/18.
 */
public abstract class Source<T> {

    public static HashMap<String, Source> source = new HashMap();

    private int bufSize;
    private int breakTime;
    private short[] gcArr;
    private long beforeTime = -1;
    private int cur;
    private String key;
    private Object[] values;
    private int[] keys;

    public Source(String key) {
        this(key, 10, 1000);
    }

    public Source(String key, int bufSize) {
        this(key, bufSize, 1000);
    }

    public String getKey() {
        return key;
    }

    public Source(String key, int bufSize, int breakTime) {
        this.bufSize = bufSize;
        this.breakTime = breakTime;
        this.key = key;
        this.cur = 0;
        gcArr = new short[bufSize];
        source.put(key, this);
    }

    public static Source getSource(String key) {
        return source.get(key);
    }

    public void put(T obj, long time) {
        // 验证值是否有效
        if (put(obj)) {
            if (beforeTime == -1) {
                beforeTime = time;
            }

            if (time - beforeTime > breakTime) {
                beforeTime = time;
                // 执行垃圾回收.
                for (int i = 0; i < gcArr[(cur + 1) % bufSize]; i++) {
                    remove();
                }
                gcArr[(cur + 1) % bufSize] = 0;
            } else {
                gcArr[cur]++;
            }
        }
    }

    public abstract LinkedList<T> list();

    protected abstract boolean put(T obj);

    protected abstract void remove();

}
