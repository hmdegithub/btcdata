package com.hm.libb.dsource;

import com.hm.libb.dsource.depict.BitCoin;
import com.hm.libb.dsource.depict.BitUrl;
import com.hm.libb.dsource.depict.BitView;
import com.hm.libb.dsource.depict.ViewDepict;
import com.hm.libb.dsource.net.websocket.BaseWebsocket;
import com.hm.libb.dsource.pline.PipeInner;
import com.hm.libb.dsource.pline.btc.*;
import com.hm.libb.dsource.pline.impl.InnerPipeRelation;
import com.hm.libb.dsource.pline.impl.OutPipeRelation;
import com.hm.libb.dsource.pline.impl.SimplePipeLine;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private static List<ResultPipe> resultList = new ArrayList<ResultPipe>(3);

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        // BaseWebsocket.runSocket(new ChbtcClientWorker(new Wear[]{new MACDWear("chbtcethcny_kline_1min")}));
        // new ChbtcSchedule().start();
        // new WalletWatchSchedule().startWear();
        HashMap<ViewDepict, KlineSourcePipe> map = new HashMap<>();
        // map.put(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.ETH, BitView.KLINE), get());
        map.put(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.ETC, BitView.KLINE), get(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.ETC, BitView.KLINE)));
        map.put(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.ETH, BitView.KLINE), get(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.ETH, BitView.KLINE)));
        map.put(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.BTC, BitView.KLINE), get(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.BTC, BitView.KLINE)));
        map.put(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.LTC, BitView.KLINE), get(new ViewDepict(BitUrl.CHBTCCLIENT, BitCoin.LTC, BitView.KLINE)));
        try {
            BaseWebsocket.runSocket(new BWork(BitUrl.CHBTCCLIENT, map));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"exit".equalsIgnoreCase(line)) {
            line = reader.readLine();
            if ("my".equalsIgnoreCase(line)) {
                for (int i = 0; i < resultList.size(); i++) {
                    ResultPipe item = resultList.get(i);
                    System.out.println(String.format("%s-%s amount[%f] money[%f] total->money[%f]\t\t",
                            item.viewDepict.getPal(), item.viewDepict.getCoin(),
                            item.amount, item.money, (item.money + item.amount * item.price)));
                }
            } else if ("show".equalsIgnoreCase(line)) {
                for (int i = 0; i < resultList.size(); i++) {
                    resultList.get(i).isShow = true;
                }
            } else {
                System.out.println("you can input[my/show]:");
            }
        }
    }

    public static KlineSourcePipe get(ViewDepict viewDepict) {
        KlineSourcePipe klineSourcePipe = new KlineSourcePipe();
        SimplePipeLine simplePipeline = new SimplePipeLine();
        simplePipeline.addSourcePipe(klineSourcePipe);

        KlinePipe kline = new KlinePipe();
        EMAPipe ema5 = new EMAPipe(5);
        EMAPipe ema12 = new EMAPipe(12);
        EMAPipe ema26 = new EMAPipe(26);
        DIFPipe dif = new DIFPipe();
        DEAPipe dea9 = new DEAPipe(9);
        MACDPipe macd = new MACDPipe();
        ResultPipe resultPipe = new ResultPipe(viewDepict);

        resultList.add(resultPipe);

        EndPricePipe endPricePipe = new EndPricePipe();
        simplePipeline.addPipeRelation(new OutPipeRelation(klineSourcePipe, endPricePipe));
        simplePipeline.addPipeRelation(new OutPipeRelation(klineSourcePipe, kline));
        simplePipeline.addPipeRelation(new InnerPipeRelation(endPricePipe, ema5));
        simplePipeline.addPipeRelation(new InnerPipeRelation(endPricePipe, ema12));
        simplePipeline.addPipeRelation(new InnerPipeRelation(endPricePipe, ema26));
        simplePipeline.addPipeRelation(new InnerPipeRelation(new PipeInner[]{ema12, ema26}, new PipeInner[]{dif}));
        simplePipeline.addPipeRelation(new InnerPipeRelation(new PipeInner[]{dif}, new PipeInner[]{dea9}));
        simplePipeline.addPipeRelation(new InnerPipeRelation(new PipeInner[]{dif, dea9}, new PipeInner[]{macd}));
        simplePipeline.addPipeRelation(new InnerPipeRelation(new PipeInner[]{kline, dif, dea9, macd, ema5, ema12, ema26}, new PipeInner[]{resultPipe}));
        return klineSourcePipe;
    }
}
