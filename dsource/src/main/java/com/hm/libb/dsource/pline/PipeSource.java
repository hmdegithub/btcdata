package com.hm.libb.dsource.pline;

/**
 * 源传输管道，数据由溜自己流出.
 */
public interface PipeSource extends Pipe {

    void addPipeline(PipeLine pipeLine);

    void startStream();

    void stopStream();
}
