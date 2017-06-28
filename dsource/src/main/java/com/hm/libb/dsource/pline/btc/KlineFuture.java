package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/26.
 */
public class KlineFuture implements Future {

    private Pipe pipe;
    private float maxPrices;
    private float minPrices;
    private float firstPrices;
    private float endPrices;
    private long time;

    public KlineFuture(Pipe pipe, long time, float minPrices, float maxPrices, float firstPrices, float endPrices) {
        this.pipe = pipe;
        this.time = time;
        this.maxPrices = maxPrices;
        this.minPrices = minPrices;
        this.firstPrices = firstPrices;
        this.endPrices = endPrices;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    public long getTime() {
        return time;
    }

    public float getMaxPrices() {
        return maxPrices;
    }

    public float getMinPrices() {
        return minPrices;
    }

    public float getFirstPrices() {
        return firstPrices;
    }

    public float getEndPrices() {
        return endPrices;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }
}
