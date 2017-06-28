package com.hm.libb.dsource.schedule;

import com.alibaba.fastjson.JSONObject;
import com.hm.libb.dsource.source.SourceDispater;
import com.hm.libb.dsource.source.TimeQueue;

import java.util.LinkedList;
import java.util.ListIterator;

import static com.hm.libb.dsource.source.SourceDispater.source;

/**
 * Created by huangming on 2017/6/19.
 */
public class ChbtcSchedule extends BaseSchedule {

    @Override
    protected void wear() {
        anyff("btc_cny_trades");
        anyff("ltc_cny_trades");
        anyff("eth_cny_trades");
        anyff("etc_cny_trades");
        anyff("bts_cny_trades");
    }

    private void anyff(String key) {
        TimeQueue source = SourceDispater.getQueue(key);
    }
}
