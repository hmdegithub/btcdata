package com.hm.libb.dsource.depict;

import java.util.Objects;

/**
 * Created by huangming on 2017/6/23.
 */
public class QuatoDepict extends BaseDepict {

    public final static String MACD = "macd";
    public final static String EMA = "ema";
    public final static String DIF = "dif";
    public final static String DEA = "dea";
    public final static String MAXPRICE = "maxprice";
    public final static String VOL = "vol";

    private String quato;

    public QuatoDepict(BitUrl pal, BitCoin coin, String quato) {
        super(pal, coin);
        this.quato = quato;
    }

    public QuatoDepict(BaseDepict bd, String quato) {
        super(bd);
        this.quato = quato;
    }

    public String getQuato() {
        return quato;
    }

    public void setQuato(String quato) {
        this.quato = quato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof QuatoDepict) {
            QuatoDepict that = (QuatoDepict) o;
            return quato.equals(that.quato) && super.equals(that);
        }
        return false;
    }

}
