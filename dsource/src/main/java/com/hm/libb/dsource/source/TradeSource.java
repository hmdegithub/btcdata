package com.hm.libb.dsource.source;

import com.alibaba.fastjson.JSONObject;
import com.hm.libb.dsource.source.Source;
import com.hm.libb.dsource.wear.Wear;

import java.util.LinkedList;

/**
 * Created by huangming on 2017/6/18.
 */
public class TradeSource extends Source<JSONObject> {

    private LinkedList<JSONObject> mem = new LinkedList<>();
    private LinkedList<Long> index = new LinkedList<>();

    public TradeSource(String key) {
        super(key);
    }

    public TradeSource(String key, int bufSize) {
        super(key, bufSize);
    }

    public TradeSource(String key, int bufSize, int breakTime) {
        super(key, bufSize, breakTime);
    }

    @Override
    public LinkedList<JSONObject> list() {
        return (LinkedList<JSONObject>) mem.clone();
    }

    @Override
    protected boolean put(JSONObject obj) {
        long key = obj.getLongValue("tid");
        if (this.index.contains(key)) {
            return false;
        } else {
            mem.addLast(obj);
            index.addLast(key);
        }
        return true;
    }

    @Override
    protected void remove() {
        mem.removeLast();
        index.removeLast();
    }
}
