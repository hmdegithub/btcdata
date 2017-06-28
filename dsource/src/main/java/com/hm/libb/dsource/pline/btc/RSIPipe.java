package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;

/**
 * Created by huangming on 2017/6/27.
 */
public class RSIPipe implements PipeInner {

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        KlineFuture future = (KlineFuture) futures[0];
        future.getEndPrices();
        return null;
    }
}
