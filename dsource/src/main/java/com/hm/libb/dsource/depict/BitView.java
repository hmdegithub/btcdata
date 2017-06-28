package com.hm.libb.dsource.depict;

/**
 * Created by huangming on 2017/6/23.
 */
public enum BitView {
    TRADE("trades"), KLINE("kline"), KLINE1M("kline1m"), KLINE5M("kline5m"), KLINE30M("kline30m"), KLINE1H("kline1h");

    private String view;
    private String currency;

    BitView(String view) {
        this.view = view;
        currency = "cny";
    }

    public String getView() {
        return view;
    }

    public String getCurrency() {
        return currency;
    }
}
