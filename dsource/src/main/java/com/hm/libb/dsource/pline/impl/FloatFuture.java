package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/26.
 */
public class FloatFuture implements Future {
    private Pipe pipe;
    private float a;

    public FloatFuture(Pipe pipe, float value) {
        this.pipe = pipe;
        this.a = value;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public void set(float a) {
        this.a = a;
    }

    public float get() {
        return a;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }
}
