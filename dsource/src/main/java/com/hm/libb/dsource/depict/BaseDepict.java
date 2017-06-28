package com.hm.libb.dsource.depict;

import java.util.Objects;

/**
 * Created by huangming on 2017/6/23.
 */
public class BaseDepict {

    private BitUrl pal;
    private BitCoin coin;

    public BaseDepict(BitUrl pal, BitCoin coin) {
        this.pal = pal;
        this.coin = coin;
    }

    public BaseDepict(BaseDepict bd) {
        this.pal = bd.pal;
        this.coin = bd.coin;
    }

    public BitUrl getPal() {
        return pal;
    }

    public BitCoin getCoin() {
        return coin;
    }

    public void setPal(BitUrl pal) {
        this.pal = pal;
    }

    public void setCoin(BitCoin coin) {
        this.coin = coin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof QuatoDepict) {
            BaseDepict that = (BaseDepict) o;
            return pal.equals(that.pal) && coin.equals(that.coin);
        }
        return false;
    }

}
