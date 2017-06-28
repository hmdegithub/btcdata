package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;

import java.time.Clock;

/**
 * Created by huangming on 2017/6/26.
 */
public class EndPricePipe implements PipeInner {

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        KlineFuture future = (KlineFuture) futures[0];
        return new TimeFloatFuture(this, future.getEndPrices(), future.getTime());
    }
}
