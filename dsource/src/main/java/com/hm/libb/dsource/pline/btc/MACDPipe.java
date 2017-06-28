package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/27.
 */
public class MACDPipe implements PipeInner {

    private static Logger LOG = Logger.getLogger(MACDPipe.class);

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        Pipe[] pipes = context.getPipes();
        if (pipes.length == 2) {
            TimeFloatFuture dif = (TimeFloatFuture) futures[0];
            TimeFloatFuture dea = (TimeFloatFuture) futures[1];
            float result = (dif.getValue() - dea.getValue()) * 2;

            return new TimeFloatFuture(this, result, dif.getTime());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
