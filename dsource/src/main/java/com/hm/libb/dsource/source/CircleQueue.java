package com.hm.libb.dsource.source;

import java.util.Arrays;

/**
 * Created by huangming on 2017/6/18.
 */
public class CircleQueue<T> implements Cloneable {

    protected int fill;
    protected int size;
    private int index;
    private int floor;
    private int capSize;
    private T[] values;

    public CircleQueue() {
        this(6);
    }

    public CircleQueue(int pow) {
        this.size = 1 << pow;
        this.fill = this.size - 1;
        this.index = -1;
        values = (T[]) new Object[size];
    }

    public T get(int index) {
        return values[(this.index - index) & fill];
    }

    public void set(T t, int index) {
        values[(this.index - index) & fill] = t;
    }

    public void add(T t) {
        ++index;
        if (capSize < size) {
            capSize++;
        } else {
            floor++;
        }
        index = index & fill;
        values[index] = t;
    }

    public void removeLast() {
        capSize--;
        values[floor++ & fill] = null;
    }

    public int size() {
        return capSize;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CircleQueue circleQueue = new CircleQueue();
        circleQueue.fill = this.fill;
        circleQueue.size = this.size;
        circleQueue.index = this.index;
        circleQueue.floor = this.floor;
        circleQueue.capSize = this.capSize;
        circleQueue.values = new Object[this.size()];
        System.arraycopy(this.values, 0, values, 0, this.values.length);
        return circleQueue;
    }
}
