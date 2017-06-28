package com.hm.libb.dsource.source;

/**
 * Created by huangming on 2017/6/18.
 */
public final class TimeOneQueue<T> extends TimeQueue<T> {

    private short[] counts;
    private int index;
    private long preious;

    public TimeOneQueue() {
        this(1000);
    }

    public TimeOneQueue(long timeSpace) {
        this(6, timeSpace);
    }

    public TimeOneQueue(int pow, long timeSpace) {
        super(pow, timeSpace);
        this.preious = -1;
        counts = new short[1 << pow];
    }

    public void add(T o, long time) {
        if (preious == -1) {
            preious = time;
        }
        if (preious == time) {
            set(o, index);
        } else {
            index++;
            index = index & fill;
            add(o);
        }
    }

}
