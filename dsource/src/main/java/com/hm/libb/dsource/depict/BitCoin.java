package com.hm.libb.dsource.depict;

/**
 * Created by huangming on 2017/6/23.
 */
public enum BitCoin {

    BTC("btc"), LTC("ltc"), ETH("eth"), ETC("etc");

    private String coin;

    BitCoin(String coin) {
        this.coin = coin;
    }

    public String getCoin() {
        return coin;
    }
}
