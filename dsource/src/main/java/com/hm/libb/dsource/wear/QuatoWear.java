package com.hm.libb.dsource.wear;

import com.hm.libb.dsource.woker.Worker;

/**
 * Created by huangming on 2017/6/23.
 */
public abstract class QuatoWear implements Wear {

    @Override
    public void wear(Worker work, String key) {
        ExprContext context = new ExprContext(work, key);
    }
}
