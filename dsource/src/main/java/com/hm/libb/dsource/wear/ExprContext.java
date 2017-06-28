package com.hm.libb.dsource.wear;

import com.hm.libb.dsource.woker.Worker;

import java.util.HashMap;

/**
 * Created by huangming on 2017/6/23.
 */
public class ExprContext {

    private String key;
    private Worker worker;
    private HashMap<String, Object> map = new HashMap<>();

    public ExprContext(Worker worker, String key) {
        this.worker = worker;
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public Worker getWorker() {
        return worker;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setValue(String key, Object obj) {
        map.put(key, obj);
    }

    public Object getValue(String key) {
        return map.get(key);
    }
}
