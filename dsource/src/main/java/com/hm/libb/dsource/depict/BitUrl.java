package com.hm.libb.dsource.depict;

/**
 * Created by huangming on 2017/6/22.
 */
public enum BitUrl {

    CHBTCCLIENT("wss://kline.chbtc.com/websocket"),
    CHBTCDEV("wss://api.chbtc.com:9999/websocket");

    private String url;

    BitUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
