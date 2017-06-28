package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.AbstractPipeSource;
import com.hm.libb.dsource.pline.Future;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/26.
 */
public class KlineSourcePipe extends AbstractPipeSource {

    private static Logger LOG = Logger.getLogger(KlineSourcePipe.class);

    @Override
    public void startStream() {
    }

    @Override
    public void push(Future future) {
        super.push(future);
    }

    @Override
    public void stopStream() {

    }
}
