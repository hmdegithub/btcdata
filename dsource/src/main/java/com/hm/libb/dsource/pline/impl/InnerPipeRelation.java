package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeRelation;

/**
 * Created by huangming on 2017/6/26.
 */
public class InnerPipeRelation implements PipeRelation {

    private PipeInner[] src;
    private PipeInner[] dest;

    public InnerPipeRelation(PipeInner src, PipeInner dest) {
        this.src = new PipeInner[]{src};
        this.dest = new PipeInner[]{dest};
    }

    public InnerPipeRelation(PipeInner[] src, PipeInner[] dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public Pipe[] getDependPipes() {
        return src;
    }

    @Override
    public Pipe[] getPipes() {
        return dest;
    }
}
