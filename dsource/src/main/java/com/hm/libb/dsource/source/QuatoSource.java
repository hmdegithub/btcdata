package com.hm.libb.dsource.source;

import com.alibaba.fastjson.JSONArray;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by huangming on 2017/6/18.
 */
public class QuatoSource extends Source<JSONArray> {

    private LinkedList<JSONArray> mem = new LinkedList<>();

    public QuatoSource(String key) {
        super(key);
    }

    public QuatoSource(String key, int bufSize) {
        super(key, bufSize);
    }

    public QuatoSource(String key, int bufSize, int breakTime) {
        super(key, bufSize, breakTime);
    }

    @Override
    public LinkedList<JSONArray> list() {
        return (LinkedList<JSONArray>) mem.clone();
    }

    @Override
    protected boolean put(JSONArray obj) {
        if (mem.size() > 0) {
            long timeNew = obj.getLongValue(0);
            long timeOld = mem.getLast().getLongValue(0);
            if (timeNew > timeOld) {
                mem.addLast(obj);
                return true;
            }
            if (timeNew == timeOld) {
                mem.removeLast();
                mem.addLast(obj);
                return true;
            }
            ListIterator<JSONArray> iterator = mem.listIterator(mem.size());
            JSONArray tempBE = null;
            JSONArray tempAF = null;
            if (iterator.hasPrevious()) {
                tempAF = iterator.previous();
            }
            while (iterator.hasPrevious()) {
                tempBE = iterator.previous();
                long timeAF = tempAF.getLongValue(0);
                long timeBE = tempBE.getLongValue(0);
                if (timeAF > timeNew && timeNew > timeBE) {
                    iterator.add(obj);
                    return true;
                }
            }
            mem.addFirst(obj);
        } else {
            mem.addLast(obj);
        }
        return true;
    }

    @Override
    protected void remove() {
        mem.removeLast();
    }
}
