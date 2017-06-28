package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeContext;

/**
 * Created by huangming on 2017/6/26.
 */
public class BPipeInner implements PipeInner {
    @Override
    public Future transform(PipeContext context) {
        Pipe[] pipes = context.getPipes();
        IntegerFuture future = (IntegerFuture) context.getFuture(pipes[0]);
        if (future.get() > 2000) {
            return null;
        }
        return new IntegerFuture(this, future.get() + 1);
    }
}
