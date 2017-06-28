package com.hm.libb.dsource.source;

/**
 * Created by huangming on 2017/6/18.
 */
public abstract class TimeQueue<T> extends CircleQueue<T> {
    public TimeQueue() {
        super();
    }

    public TimeQueue(int pow) {
        super(pow);
    }

    public TimeQueue(int pow, long timeSpace) {
        super(pow);
        this.timeSpace = timeSpace;
    }

    protected long timeSpace;


    public abstract void add(T o, long time);
}
