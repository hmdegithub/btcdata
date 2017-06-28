package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/26.
 */
public class IntegerFuture implements Future {
    private Pipe pipe;
    private int a;

    public IntegerFuture(Pipe pipe, int value) {
        this.pipe = pipe;
        this.a = value;
    }

    public void set(int a) {
        this.a = a;
    }

    public int get() {
        return a;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }
}
