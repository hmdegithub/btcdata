package com.hm.libb.dsource.source;

/**
 * Created by huangming on 2017/6/18.
 */
public final class TimeSpaceQueue<T> extends TimeQueue<T> {

    private short[] counts;
    private int index;
    private long timeSpace;
    private long preious;

    public TimeSpaceQueue() {
        this(1000);
    }

    public TimeSpaceQueue(long timeSpace) {
        this(6, timeSpace);
    }

    public TimeSpaceQueue(int pow, long timeSpace) {
        super(pow, timeSpace);
        this.preious = -1;
        counts = new short[1 << pow];
    }

    @Override
    public void add(T o) {
        add(o, System.currentTimeMillis());
    }

    public void add(T o, long time) {
        if (preious == -1) {
            preious = time;
        }
        if (time - preious > timeSpace) {
            preious = time;
            counts[index]++;
        } else {
            index++;
            index = index & fill;
            if (counts[index] != 0) {
                for (int i = 1; i <= counts[index]; i++) {
                    removeLast();
                }
            }
        }
        super.add(o);
    }

}
