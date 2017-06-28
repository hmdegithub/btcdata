package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/27.
 */
public class DIFPipe implements PipeInner {

    private Logger LOG = Logger.getLogger(DIFPipe.class);

    public DIFPipe() {
    }

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        Pipe[] pipes = context.getPipes();
        if (pipes.length == 2) {
            TimeFloatFuture ema = (TimeFloatFuture) futures[0];
            TimeFloatFuture ema2 = (TimeFloatFuture) futures[1];
            float result = ema.getValue() - ema2.getValue();
            return new TimeFloatFuture(this, result, ema.getTime());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
