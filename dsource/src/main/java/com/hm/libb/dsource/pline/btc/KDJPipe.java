package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;

/**
 * Created by huangming on 2017/6/27.
 */
public class KDJPipe implements PipeInner {

    private KDJFuture cacheKdj;
    private KDJFuture lkdj;
    private long ptime;

    private static final float A = 2 / 3;
    private static final float B = 1 / 3;

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        TimeFloatFuture future = (TimeFloatFuture) futures[0];
        float rsv = future.getValue();
        float k;
        float d;
        if (cacheKdj == null) {
            k = 50;
            d = 50;
        } else {
            if (future.getTime() > ptime + 999) {
                cacheKdj = lkdj;
            }
            k = A * cacheKdj.getK() + B * rsv;
            d = A * cacheKdj.getD() + B * k;
        }
        ptime = future.getTime();
        float j = 3 * k - 2 * d;
        lkdj = new KDJFuture(this, k, d, j, future.getTime());
        if (cacheKdj == null) {
            cacheKdj = lkdj;
        }
        return lkdj;
    }
}
