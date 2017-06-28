package com.hm.libb.dsource.pline.btc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hm.libb.dsource.depict.BitUrl;
import com.hm.libb.dsource.depict.ViewDepict;
import com.hm.libb.dsource.woker.Worker;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangming on 2017/6/27.
 */
public class BWork extends Worker {

    private static Logger LOG = Logger.getLogger(BWork.class);
    private HashMap<String, KlineSourcePipe> map = new HashMap<String, KlineSourcePipe>();
    private HashMap<ViewDepict, KlineSourcePipe> map2 = new HashMap<ViewDepict, KlineSourcePipe>();

    public BWork(BitUrl bitUrl, HashMap<ViewDepict, KlineSourcePipe> map) {
        super(bitUrl);
        map2.putAll(map);
        Set<Map.Entry<ViewDepict, KlineSourcePipe>> entries = map.entrySet();
        for (Map.Entry<ViewDepict, KlineSourcePipe> entry : entries) {
            this.map.put(getChannel(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public void Close(Channel channel) {

    }

    @Override
    public void init(Channel channel) {
        Set<ViewDepict> keys = map2.keySet();
        for (ViewDepict key : keys) {
            AddChannelCommand(channel, key);
        }
    }

    @Override
    public String receive(String msg) {
        JSONObject s = JSONObject.parseObject(msg);
        String key = s.getString("channel");
        if (key.contains("_kline_")) {
            JSONObject datas = s.getJSONObject("datas");
            JSONArray dataArr = datas.getJSONArray("data");
            for (int i = 0; i < dataArr.size(); i++) {
                JSONArray o = dataArr.getJSONArray(i);
                KlineSourcePipe sourcePipe = map.get(key);
                sourcePipe.push(new KlineFuture(sourcePipe, o.getLongValue(0), o.getFloatValue(1), o.getFloatValue(2), o.getFloatValue(3), o.getFloatValue(4)));
            }
        }
        return null;
    }
}
