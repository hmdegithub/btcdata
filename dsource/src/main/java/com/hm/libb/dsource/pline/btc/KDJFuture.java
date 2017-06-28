package com.hm.libb.dsource.pline.btc;


import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/27.
 */
public class KDJFuture implements Future {

    private Pipe pipe;
    private float k;
    private float d;
    private float j;
    private long time;

    public KDJFuture(Pipe pipe, float k, float d, float j, long time) {
        this.pipe = pipe;
        this.k = k;
        this.d = d;
        this.j = j;
        this.time = time;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public float getJ() {
        return j;
    }

    public void setJ(float j) {
        this.j = j;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
