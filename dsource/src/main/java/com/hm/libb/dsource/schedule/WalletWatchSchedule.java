package com.hm.libb.dsource.schedule;

import org.apache.log4j.Logger;

/**
 * Created by huangming on 2017/6/22.
 */
public class WalletWatchSchedule extends BaseSchedule {

    private static Logger LOG = Logger.getLogger(WalletWatchSchedule.class);

    public static float money = 1000;
    public static float amount = 0;

    public WalletWatchSchedule() {
        super(30000);
    }

    @Override
    protected void wear() {
        LOG.info(String.format("Wallet -- MONEY[%s] AMOUNT[%s]", money, amount));
    }
}
