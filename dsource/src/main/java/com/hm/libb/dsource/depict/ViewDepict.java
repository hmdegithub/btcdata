package com.hm.libb.dsource.depict;

import java.util.Objects;

/**
 * Created by huangming on 2017/6/23.
 */
public class ViewDepict extends BaseDepict {

    private BitView view;

    public ViewDepict(BitUrl pal, BitCoin coin, BitView view) {
        super(pal, coin);
        this.view = view;
    }

    public ViewDepict(BaseDepict bd, BitView view) {
        super(bd);
        this.view = view;
    }

    public BitView getView() {
        return view;
    }

    public void setView(BitView view) {
        this.view = view;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof QuatoDepict) {
            ViewDepict that = (ViewDepict) o;
            return view.equals(that.view) && super.equals(that);
        }
        return false;
    }

}

