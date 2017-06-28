package com.hm.libb.dsource.pline.btc;

import com.hm.libb.dsource.depict.ViewDepict;
import com.hm.libb.dsource.pline.Future;
import com.hm.libb.dsource.pline.PipeContext;
import com.hm.libb.dsource.pline.PipeInner;
import org.apache.log4j.Logger;
import org.omg.CORBA.portable.UnknownException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Clock;


/**
 * Created by huangming on 2017/6/27.
 */
public class ResultPipe implements PipeInner {
    private static Logger LOG = Logger.getLogger(ResultPipe.class);

    public ViewDepict viewDepict;

    public static double IMONEY = 1000;
    public static double IAMOUNT = 0;

    public double amount = IAMOUNT;
    public double money = IMONEY;
    private byte count = 0;
    public boolean isShow = false;
    public float price = 0;

    public ResultPipe(ViewDepict viewDepict) {
        this.viewDepict = viewDepict;
    }

    @Override
    public Future transform(PipeContext context) {
        Future[] futures = context.getFutures();
        KlineFuture kline = (KlineFuture) futures[0];
        TimeFloatFuture dif = (TimeFloatFuture) futures[1];
        TimeFloatFuture dea = (TimeFloatFuture) futures[2];
        TimeFloatFuture macd = (TimeFloatFuture) futures[3];
        TimeFloatFuture ema5 = (TimeFloatFuture) futures[4];
        TimeFloatFuture ema12 = (TimeFloatFuture) futures[5];
        TimeFloatFuture ema26 = (TimeFloatFuture) futures[6];
        price = kline.getEndPrices();
        long time = kline.getTime();

        boolean flag = false;
        Clock clock = Clock.systemUTC();
        if (kline.getTime() > clock.millis() - 80000) {
            if (dif.getValue() > dea.getValue() && macd.getValue() > 0.3) {
                // 买买买
                synchronized (this) {
                    if (money > 1) {
                        // 获取当前钱最多买多少币. 钱/单价=数量
                        // 数量保留6位小数
                        // 验证 金额-单价*数量 < 0
                        // 扣除 单价*数量 的金额
                        // 将买的币数量增加
                        BigDecimal num = BigDecimal.valueOf(money).divide(BigDecimal.valueOf(price), 6, BigDecimal.ROUND_FLOOR);
                        amount = num.doubleValue();
                        BigDecimal costMoney = BigDecimal.valueOf(price).multiply(num);
                        if (money < costMoney.doubleValue()) {
                            throw new RuntimeException(viewDepict.getPal().toString() + viewDepict.getCoin() + "出现了金额小于花费金额的情况");
                        }
                        LOG.info(String.format("%s-%s buy:[%f],cost:[%f] time:[%d]", viewDepict.getPal(), viewDepict.getCoin(), amount, costMoney.doubleValue(), time));
                        money = BigDecimal.valueOf(money).subtract(BigDecimal.valueOf(price).multiply(num)).doubleValue();
                        flag = true;
                    }
                }
            } else if (dif.getValue() < dea.getValue() || macd.getValue() < 0.2) {
                // 卖卖卖
                synchronized (this) {
                    if (amount > 0.1) {
                        // 将当前拥有的币悉数卖出
                        // 金额 += 单价*数量
                        // 数量 = 0
                        BigDecimal mm = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(price));
                        money = BigDecimal.valueOf(money).add(mm).doubleValue();
                        LOG.info(String.format("%s-%s sell:[%f],get:[%f] time:[%d]", viewDepict.getPal(), viewDepict.getCoin(), amount, money, time));
                        amount = 0;
                        flag = true;
                    }
                }
            }
        }
        int i = count++ & 0xfff;
        if (i == 0xfff || flag || isShow) {
            i = 0;
            LOG.info(String.format("%s-%s closed[%f] dif[%f] dea[%f] macd[%f] ema12[%f] ema26[%f]",
                    viewDepict.getPal(), viewDepict.getCoin(),
                    price, dif.getValue(), dea.getValue(), macd.getValue(),
                    ema12.getValue(), ema26.getValue()));
            isShow = false;
        }
        return null;
    }

}
