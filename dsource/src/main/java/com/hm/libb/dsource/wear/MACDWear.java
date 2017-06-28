package com.hm.libb.dsource.wear;

import com.alibaba.fastjson.JSONArray;
import com.hm.libb.dsource.source.SourceDispater;
import com.hm.libb.dsource.source.TimeQueue;
import com.hm.libb.dsource.util.CalUtils;
import com.hm.libb.dsource.woker.Worker;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by huangming on 2017/6/22.
 */
public class MACDWear implements Wear {

    private static Logger LOG = Logger.getLogger(MACDWear.class);
    private float amount = 0;
    private float money = 1000;
    private String key;

    public MACDWear(String key) {
        this.key = key;
    }

    public void wear(Worker work, String key) {
        if (this.key.equals(key)) {
            TimeQueue<JSONArray> queue = SourceDispater.getQueue(key);
            if (queue != null) {
                float[] arr = new float[queue.size()];
                for (int i = queue.size(); i > 0; i--) {
                    arr[i] = queue.get(i).getLongValue(1);
                }
                float bprice = arr[0];
                float ema5 = CalUtils.EMA(arr, 5);
                float ema10 = CalUtils.EMA(arr, 10);
                float ema30 = CalUtils.EMA(arr, 30);
                float dif = CalUtils.DIF(arr, 12, 26);
                float dea = CalUtils.DEA(arr, 12, 26, 9);
                float bar = CalUtils.BAR(arr, 12, 26, 9);

                if (dif >= dea && bar > 0 && money > 0) {
                    // 买买买
                    amount = money / bprice;
                    money = 0;
                    LOG.info(String.format("buy:[%f],cost:[%f]", amount, amount * bprice));
                    print();
                } else if (dif <= dea && amount > 0) {
                    // 卖卖卖
                    money = amount * bprice;
                    amount = 0;
                    LOG.info(String.format("sell:[%f],get:[%f]", money, money / bprice));
                    print();
                }
                LOG.info(String.format("EMA5:[%f] EMA10:[%f] EMA30:[%f] DIF:[%f] DEA:[%f] BAR:[%f]", ema5, ema10, ema30, dif, dea, bar));
            }
        }
    }

    public void print() {
        LOG.info(String.format(" ====== TYPE:[%s] ========", "ETC"));
        // LOG.info(String.format("EMA5:[%f] EMA10:[%f] EMA30:[%f] DIF:[%f] DEA:[%f] BAR:[%f]", bema5, bema10, bema30, bdif, bdea, bbar));
        // LOG.info(String.format(" ====MONERY:[%f] AMOUNT:[%f]=>TOMOENY:[%f]====", money, amount, amount * bprice));
    }
}
