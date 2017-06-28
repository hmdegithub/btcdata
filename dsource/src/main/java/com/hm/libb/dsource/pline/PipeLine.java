package com.hm.libb.dsource.pline;

/**
 * 管道流，描述所有管道关系，以及管道开关.
 */
public interface PipeLine {

    void push(Future e);

    void addSourcePipe(PipeSource pipeSource);

    void start();

    void end(PipeContext pipeContext);

    void addPipeRelation(PipeRelation pipeRelation);

}
