package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.impl.*;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by huangming on 2017/6/27.
 */
public class KlineSourcePipeTest extends TestCase {

    @Test
    public void test() {
        int comprec = ~(Integer.MAX_VALUE << 10);
        System.out.println(Integer.toBinaryString(comprec));
        int rec = 0;
        rec |= 1 << 5;
        for (int i = 0; i < 10; i++) {
            rec |= 1 << i;
            System.out.println(Integer.toBinaryString(rec));
        }
        System.out.println(Integer.toBinaryString(0x1f));

    }

}