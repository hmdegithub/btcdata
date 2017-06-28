package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.Pipe;
import com.hm.libb.dsource.pline.PipeRelation;
import com.hm.libb.dsource.pline.PipeSource;

/**
 * Created by huangming on 2017/6/26.
 */
public class OutPipeRelation implements PipeRelation {

    private PipeSource[] src;
    private PipeInner[] dest;


    public OutPipeRelation(PipeSource src, PipeInner dest) {
        this.src = new PipeSource[]{src};
        this.dest = new PipeInner[]{dest};
    }

    public OutPipeRelation(PipeSource[] src, PipeInner[] dest) {
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
