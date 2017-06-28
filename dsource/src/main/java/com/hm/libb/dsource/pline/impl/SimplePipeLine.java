package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.*;

import java.util.ArrayList;

/**
 * 简易版管道流.
 * Created by huangming on 2017/6/26.
 */
public class SimplePipeLine extends AbstractPipeLine {

    private int total;

    private ArrayList<PipeSource> pipeSourceArr = new ArrayList<>(5);
    private ArrayList<PipeInner> pipeArr = new ArrayList<>(10);
    private ArrayList<PipeRelation> pipeRelationArr = new ArrayList<>(10);
    private ArrayList<PipeRelation> outPipeRelationArr = new ArrayList<>(10);
    private ArrayList<PushManager> pushManagers = new ArrayList<>(30);

    @Override
    public void addSourcePipe(PipeSource pipeSource) {
        pipeSourceArr.add(pipeSource);
        pipeSource.addPipeline(this);
    }

    @Override
    public void addPipeRelation(PipeRelation pipeRelation) {
        if (pipeRelation instanceof OutPipeRelation) {
            outPipeRelationArr.add(pipeRelation);
            Pipe[] pipes = pipeRelation.getPipes();
            for (int i = 0; i < pipes.length; i++) {
                if (!pipeArr.contains(pipes[i])) {
                    pipeArr.add((PipeInner) pipes[i]);
                }
            }
            pushManagers.add(new PushManager(this, (PipeInner[]) pipeRelation.getPipes(), pipeRelation.getDependPipes()));
        }

        if (pipeRelation instanceof InnerPipeRelation) {
            pipeRelationArr.add(pipeRelation);
            pushManagers.add(new PushManager(this, (PipeInner[]) pipeRelation.getPipes(), pipeRelation.getDependPipes()));
        }
    }

    @Override
    public void start() {
        for (int i = 0, length = pipeSourceArr.size(); i < length; i++) {
            pipeSourceArr.get(i).startStream();
        }
    }

    @Override
    public void end(PipeContext pipeContext) {
        for (int i = 0, length = pipeSourceArr.size(); i < length; i++) {
            pipeSourceArr.get(i).stopStream();
        }
        System.out.println(total);
    }

    /**
     * 推送管道流数据.
     *
     * @param future
     */
    @Override
    public void push(Future future) {
        ArrayList<Future> result = new ArrayList<>(20);
        for (int i = 0; i < pushManagers.size(); i++) {
            PushManager pushManager = pushManagers.get(i);
            if (pushManager.checkFuture(future)) {
                pushManager.push(future, result);
            }
        }
        for (int i = 0; i < result.size(); i++) {
            push(result.get(i));
        }
    }

    /**
     * 管道推送管理器，用于处理内部推送.
     */
    private static class PushManager {

        private Future[] futures;
        private Pipe[] dependPipes;
        private PipeInner[] pipes;
        private int rec;
        private int comprec;
        private PipeLine pipeLine;

        public PushManager(PipeLine pipeLine, PipeInner[] pipes, Pipe[] dependPipes) {
            this.dependPipes = dependPipes;
            futures = new Future[dependPipes.length];
            this.pipes = pipes;
            this.pipeLine = pipeLine;
            rec = 0;
            comprec = ~(Integer.MAX_VALUE << dependPipes.length);
        }

        public boolean checkFuture(Future future) {
            Pipe pipe = future.getPipe();
            for (int i = 0; i < dependPipes.length; i++) {
                if (dependPipes[i] == pipe) {
                    return true;
                }
            }
            return false;
        }

        public void push(Future future, ArrayList<Future> resultSet) {
            // 填充结果
            Pipe pipe = future.getPipe();
            for (int i = 0; i < dependPipes.length; i++) {
                if (dependPipes[i] == pipe) {
                    this.futures[i] = future;
                    rec |= 1 << i;
                }
            }
            // 检验结果是否填充完毕
            if (rec == comprec) {
                rec = 0;
                PipeContext pipeContext = new PipeContext(pipeLine, dependPipes, this.futures);
                // 执行
                for (int i = 0; i < pipes.length; i++) {
                    Future transform = pipes[i].transform(pipeContext);
                    if (transform != null) {
                        resultSet.add(transform);
                    }
                }
            }
        }
    }

}
