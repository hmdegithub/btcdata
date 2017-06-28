package com.hm.libb.dsource.woker;

import com.alibaba.fastjson.JSONObject;
import com.hm.libb.dsource.depict.BitCoin;
import com.hm.libb.dsource.depict.BitUrl;
import com.hm.libb.dsource.depict.BitView;
import com.hm.libb.dsource.depict.ViewDepict;
import com.hm.libb.dsource.net.websocket.BaseReceive;
import com.hm.libb.dsource.res.SimpleThreadLocal;
import com.hm.libb.dsource.wear.Wear;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by huangming on 2017/6/22.
 */
public abstract class Worker extends BaseReceive {

    private List<Wear> wArr;
    private BitUrl bitUrl;

    private static Logger LOG = Logger.getLogger(Worker.class);

    public Worker(BitUrl bitUrl) {
        this.bitUrl = bitUrl;
    }

    public Worker(BitUrl bitUrl, Wear[] wears) {
        if (wears != null)
            wArr = Arrays.asList(wears);
        this.bitUrl = bitUrl;
    }

    public static String AddChannelCommand(Channel channel, ViewDepict vd) {
        String command = getAddChannelCommand(vd);
        channel.writeAndFlush(new TextWebSocketFrame(command));
        LOG.info(command);
        return command;
    }

    @Override
    public void onReceive(String msg) {
        SimpleThreadLocal.execute(new WearThread(this, msg));
    }

    public abstract String receive(String msg);

    public static String getAddChannelCommand(ViewDepict vd) {
        BitUrl pal = vd.getPal();
        BitCoin coin = vd.getCoin();
        BitView view = vd.getView();

        if (BitUrl.CHBTCDEV.equals(pal) || BitUrl.CHBTCCLIENT.equals(pal)) {
            if (BitView.TRADE.equals(view)) {
                JSONObject data = new JSONObject();
                data.put("event", "addChannel");
                data.put("channel", coin.getCoin() + "_" + view.getCurrency() + "_lasttrades");
                data.put("isZip", "false");
                return data.toJSONString();
            }
            if (BitView.KLINE.equals(view) || BitView.KLINE1M.equals(view)) {
                JSONObject data = new JSONObject();
                data.put("event", "addChannel");
                data.put("channel", "chbtc" + coin.getCoin() + view.getCurrency() + "_kline_1min");
                Calendar cal = Calendar.getInstance();
                int minute = cal.get(Calendar.MINUTE);
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - 5);
                cal.set(Calendar.MINUTE, minute / 5 * 5);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                data.put("lastTime", cal.getTimeInMillis());
                data.put("isZip", "false");
                return data.toJSONString();
            }
        }

        return "";
    }

    public static String getChannel(ViewDepict vd) {
        BitUrl pal = vd.getPal();
        BitCoin coin = vd.getCoin();
        BitView view = vd.getView();

        if (BitUrl.CHBTCDEV.equals(pal) || BitUrl.CHBTCCLIENT.equals(pal)) {
            if (BitView.TRADE.equals(view)) {
                return coin.getCoin() + "_" + view.getCurrency() + "_lasttrades";
            }
            if (BitView.KLINE.equals(view) || BitView.KLINE1M.equals(view)) {
                return "chbtc" + coin.getCoin() + view.getCurrency() + "_kline_1min";
            }
        }
        return "";
    }

    public BitUrl getBitUrl() {
        return bitUrl;
    }

    private static class WearThread implements Runnable {
        private Worker worker;
        private String msg;

        public WearThread(Worker worker, String msg) {
            this.worker = worker;
            this.msg = msg;
        }

        @Override
        public void run() {
            worker.receive(msg);
        }
    }
}
