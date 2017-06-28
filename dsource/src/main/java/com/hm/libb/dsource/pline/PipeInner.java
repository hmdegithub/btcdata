package com.hm.libb.dsource.pline;

/**
 * Created by huangming on 2017/6/26.
 */
public interface PipeInner extends Pipe {
    /**
     * 传输.
     *
     * @param context
     * @return
     */
    Future transform(PipeContext context);
}
