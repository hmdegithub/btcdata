package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.impl.FloatFuture;
import com.hm.libb.dsource.source.CircleQueue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by huangming on 2017/6/27.
 */
public class RSVPipe implements PipeInner {

    private short top;
    private ConcurrentLinkedQueue<Float> minQueue;
    private ConcurrentLinkedQueue<Float> maxQueue;

    public RSVPipe(short top) {
        this.top = top;
        minQueue = new ConcurrentLinkedQueue<Float>();
        maxQueue = new ConcurrentLinkedQueue<Float>();
    }

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        KlineFuture future = (KlineFuture) futures[0];

        minQueue.add(future.getMinPrices());
        maxQueue.add(future.getMaxPrices());

        if (minQueue.size() > top) {
            minQueue.poll();
        }
        if (maxQueue.size() > top) {
            maxQueue.poll();
        }
        float max = maxQueue.peek();
        float min = minQueue.peek();
        for (Float aFloat : maxQueue) {
            if (max < aFloat) {
                max = aFloat;
            }
        }
        for (Float aFloat : minQueue) {
            if (min > aFloat) {
                min = aFloat;
            }
        }
        return new TimeFloatFuture(this, (future.getEndPrices() - min) / (max - future.getEndPrices()) * 100, future.getTime());
    }
}
