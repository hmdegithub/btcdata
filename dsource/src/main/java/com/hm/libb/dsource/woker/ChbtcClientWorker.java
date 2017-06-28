package com.hm.libb.dsource.woker;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hm.libb.dsource.depict.BitCoin;
import com.hm.libb.dsource.depict.BitUrl;
import com.hm.libb.dsource.depict.BitView;
import com.hm.libb.dsource.depict.ViewDepict;
import com.hm.libb.dsource.source.SourceDispater;
import com.hm.libb.dsource.source.TimeQueue;
import com.hm.libb.dsource.wear.Wear;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/22.
 */
public class ChbtcClientWorker extends Worker {

    private static Logger LOG = Logger.getLogger(ChbtcClientWorker.class);

    public ChbtcClientWorker(Wear[] wears) {
        super(BitUrl.CHBTCCLIENT, wears);
    }

    public ChbtcClientWorker() {
        super(BitUrl.CHBTCCLIENT);
    }

    @Override
    public String receive(String msg) {
        JSONObject s = JSONObject.parseObject(msg);
        String key = s.getString("channel");
        TimeQueue source = SourceDispater.getQueue(key);
        if (source == null) return key;
        if (key.contains("_kline_")) {
            JSONObject datas = s.getJSONObject("datas");
            JSONArray dataArr = datas.getJSONArray("data");
            for (int i = 0; i < dataArr.size(); i++) {
                JSONArray o = dataArr.getJSONArray(i);
                source.add(o, o.getLongValue(0));
            }
        } else if (key.endsWith("trades")) {
            JSONArray arr = s.getJSONArray("data");
            for (int i = 0; i < arr.size(); i++) {
                JSONObject p = arr.getJSONObject(i);
                source.add(p, p.getLongValue("date"));
            }
        }
        LOG.info(msg);
        return key;
    }

    @Override
    public void init(Channel channel) {
        ViewDepict viewDepict = new ViewDepict(getBitUrl(), BitCoin.ETC, BitView.KLINE);
        AddChannelCommand(channel, viewDepict);
        viewDepict.setView(BitView.TRADE);
        AddChannelCommand(channel, viewDepict);
        viewDepict.setCoin(BitCoin.ETH);
        AddChannelCommand(channel, viewDepict);
        viewDepict.setView(BitView.KLINE);
        AddChannelCommand(channel, viewDepict);

        SourceDispater.createQueueOne("chbtcethcny_kline_1min", 1);
        SourceDispater.createQuqeueSpace("eth_cny_lasttrades", 2000);
        SourceDispater.createQueueOne("chbtcetccny_kline_1min", 1);
        SourceDispater.createQuqeueSpace("etc_cny_lasttrades", 2000);
    }

    @Override
    public void Close(Channel channel) {

    }
}
