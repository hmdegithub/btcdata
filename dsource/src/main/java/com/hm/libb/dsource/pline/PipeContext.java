package com.hm.libb.dsource.pline;

/**
 * 管道上下文，用于管道直接交流.
 */
public class PipeContext {

    private Pipe[] src;
    private Future[] futures;
    private PipeLine pipeLine;

    public PipeContext(PipeLine pipeLine, Pipe[] src, Future[] futures) {
        this.src = src;
        this.futures = futures;
        this.pipeLine = pipeLine;
    }

    public Pipe[] getPipes() {
        return src;
    }

    public Future[] getFutures() {
        return futures;
    }

    public Future getFuture(Pipe pipe) {
        for (int i = 0; i < src.length; i++) {
            if (src[i] == pipe) {
                return futures[i];
            }
        }
        throw new IllegalStateException("not find future!");
    }
}
