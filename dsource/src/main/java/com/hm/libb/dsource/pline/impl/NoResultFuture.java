package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.Pipe;

/**
 * Created by huangming on 2017/6/26.
 */
public class NoResultFuture implements Future {

    private Pipe pipe;

    public NoResultFuture(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public Pipe getPipe() {
        return pipe;
    }
}
