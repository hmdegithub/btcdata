package com.hm.libb.dsource.pline.impl;

import com.hm.libb.dsource.pline.AbstractPipeSource;
import com.hm.libb.dsource.pline.PipeInner;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by huangming on 2017/6/26.
 */
public class SimplePipeLineTest extends TestCase {

    class DataPipeSource extends AbstractPipeSource {
        public DataPipeSource() {
            super();
        }

        @Override
        public void startStream() {
            IntegerFuture simpleFuture = new IntegerFuture(this, 1);
            push(simpleFuture);
        }

        @Override
        public void stopStream() {

        }
    }

    @Test
    public void test() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();
        SimplePipeLine simplePipeline = new SimplePipeLine();
        DataPipeSource dataSourcePipe = new DataPipeSource();
        simplePipeline.addSourcePipe(dataSourcePipe);
        APipeInner aPipe = new APipeInner();
        BPipeInner bPipe = new BPipeInner();
        simplePipeline.addPipeRelation(new OutPipeRelation(dataSourcePipe, aPipe));
        PipeInner pre = aPipe;
        for (int i = 0; i < 30; i++) {
            PipeInner cur = new APipeInner();
            simplePipeline.addPipeRelation(new InnerPipeRelation(pre, cur));
            simplePipeline.addPipeRelation(new InnerPipeRelation(pre, new APipeInner()));
            simplePipeline.addPipeRelation(new InnerPipeRelation(pre, new APipeInner()));
            simplePipeline.addPipeRelation(new InnerPipeRelation(pre, new APipeInner()));
            pre = cur;
        }
        LocalDateTime now1 = LocalDateTime.now();
        Duration between = Duration.between(now, now1);
        simplePipeline.start();
        simplePipeline.end(null);
    }

}