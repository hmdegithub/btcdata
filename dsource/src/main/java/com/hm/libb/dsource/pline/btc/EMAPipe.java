package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/26.
 */
public class EMAPipe implements PipeInner {
    private static Logger LOG = Logger.getLogger(EMAPipe.class);

    // 参数变量
    private float c1;
    private int day;
    private float c2;

    // 状态变量
    private float cachehEma;
    private float lcachehEma;

    private long preiousTime;

    public EMAPipe(int day) {
        this.day = day;
        c1 = 2f / (day + 1f);
        c2 = (day - 1f) / (day + 1f);
    }

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        Pipe[] pipes = context.getPipes();

        float result;
        long timeNow;
        if (pipes[0].getClass() == EndPricePipe.class) {
            TimeFloatFuture endPrice = (TimeFloatFuture) futures[0];
            timeNow = endPrice.getTime();
            float value = endPrice.getValue();

            if (cachehEma == 0) {
                cachehEma = value;
                preiousTime = timeNow;
                result = cachehEma;
            } else {
                if (timeNow > preiousTime + 999) {
                    cachehEma = lcachehEma;
                    preiousTime = timeNow;
                    result = (c1 * value) + (c2 * cachehEma);
                    lcachehEma = result;
                } else {
                    result = (c1 * value) + (c2 * cachehEma);
                    lcachehEma = result;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return new TimeFloatFuture(this, result, timeNow);
    }

}
