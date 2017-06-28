package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/27.
 */
public class TimeFloatFuture implements Future {

    private Pipe pipe;
    private float value;
    private long time;

    public TimeFloatFuture(Pipe pipe, float value, long time) {
        this.pipe = pipe;
        this.value = value;
        this.time = time;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public float getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }
}
