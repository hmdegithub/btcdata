package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/27.
 */
public class DEAPipe implements PipeInner {

    private Logger LOG = Logger.getLogger(DEAPipe.class);

    private float cacheDea;
    private float lastDea;
    private long ptime;

    private int c;
    private float c1;
    private float c2;

    public DEAPipe(int c) {
        this.c = c;
        c1 = (2f) / (c + 1f);
        c2 = (c - 1f) / (c + 1f);
    }

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        Pipe[] pipes = context.getPipes();
        if (pipes[0].getClass() == DIFPipe.class) {
            TimeFloatFuture dif = (TimeFloatFuture) futures[0];
            float result = c1 * dif.getValue() + c2 * cacheDea;
            if (ptime + 999 < dif.getTime()) {
                cacheDea = lastDea;
                ptime = dif.getTime();
            }
            lastDea = result;
            return new TimeFloatFuture(this, result, dif.getTime());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
