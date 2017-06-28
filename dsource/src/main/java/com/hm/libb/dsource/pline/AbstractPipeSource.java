package com.hm.libb.dsource.pline;

import java.util.ArrayList;

public abstract class AbstractPipeSource implements PipeSource {

    private ArrayList<PipeLine> list = new ArrayList<PipeLine>(5);

    public AbstractPipeSource() {
    }

    public void addPipeline(PipeLine pipeLine) {
        list.add(pipeLine);
    }

    protected void push(Future future) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).push(future);
        }
    }

}
