package com.hm.libb.dsource.source;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by huangming on 2017/6/18.
 */
public final class SourceDispater {

    public static HashMap<String, TimeQueue> source = new HashMap();

    private SourceDispater() {
    }

    public static TimeQueue createQuqeueSpace(String key, int timeSpace) {
        TimeQueue timeQueue = source.get(key);
        if (timeQueue == null) {
            synchronized (source) {
                timeQueue = source.get(key);
                if (timeQueue == null) {
                    timeQueue = new TimeSpaceQueue(6, timeSpace);
                }
            }
        }
        source.put(key, timeQueue);
        return timeQueue;
    }

    public static TimeQueue createQueueOne(String key, int timeSpace) {
        TimeQueue timeQueue = source.get(key);
        if (timeQueue == null) {
            synchronized (source) {
                timeQueue = source.get(key);
                if (timeQueue == null) {
                    timeQueue = new TimeOneQueue(6, timeSpace);
                }
            }
        }
        source.put(key, timeQueue);
        return timeQueue;
    }

    public static TimeQueue getQueue(String key) {
        return source.get(key);
    }
}
