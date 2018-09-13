package com.runer.toumai.util;

import com.runer.liabary.util.Arith;
import com.runer.toumai.bean.BiddingPredictio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruier on 2018/7/14.
 * 竞价的预测工具；
 */
public class BiddingPredictionMachine {

    public static final int RATIO_ROUND =20 ;
    public static  final double ROUD_ADD_RATIO =1.01;
    public  static  final double GOT_RATIO =0.95;
    public static final double  OUT_RATIO =202.00;

    /**
     *生成固定轮次的价格
     * @param price
     * @param ratio
     * @param buyoutPrice
     * @param round
     * @return
     */
    public BiddingPredictio getOneBinding(Double price ,Integer ratio ,Double buyoutPrice,int round ){
        if (round<=0){
            return  null ;
        }
        //上升的比例
        Double persentRatio = Arith.div((100+ratio),100) ;
        Double total_out = 0.0 ;// //累计出局收益
        for (int i =0 ; i < round; i++) {
            //当前价
            price = Arith.mul(ROUD_ADD_RATIO,price,4);
            if (i>0){
                total_out =Arith.add(Arith.div(price,OUT_RATIO,4),total_out);
            }
            //当前一口价
            Double  arith_buyoutPrice =Arith.mul(price,persentRatio,4);
            //当前的一口价大于传入的一口价的时候
            if (arith_buyoutPrice>buyoutPrice){
                buyoutPrice =arith_buyoutPrice;
            }
        }
        //竞价的收益
        double biddingGot = Arith.sub(Arith.mul(price, GOT_RATIO), total_out);
        //一口价的收益
        double oneGot =  Arith.sub(Arith.mul(buyoutPrice,GOT_RATIO),total_out) ;

        BiddingPredictio biddingPredictio =new BiddingPredictio();
        biddingPredictio.setNum(String.valueOf(round));
        biddingPredictio.setCurrentPrice(getPrice(price));
        biddingPredictio.setBuyout_price(getPrice(buyoutPrice));
        biddingPredictio.setSellerGotPrice(getPrice(biddingGot));
        biddingPredictio.setSellerGotBuyOutPrice(getPrice(oneGot));

        return  biddingPredictio ;
    }

    /**
     *批量的生成数据'
     * @param price  底价
     * @param ratio  一口价较但钱价的上浮比例
     * @param buyoutPrice 一口价
     * @return
     */
    public List<BiddingPredictio> generatePricesByOneOneStep(Double price ,Integer ratio ,Double buyoutPrice ){

        List<BiddingPredictio> biddingPredictios =new ArrayList<>();
        //上升的比例
        Double persentRatio = Arith.div((100+ratio),100);
        Double total_out = 0.0 ;// //累计出局收益
        for (int i = 0; i <RATIO_ROUND; i++) {
            //当前价
            price = Arith.mul(ROUD_ADD_RATIO,price,4);
            if (i>0){
                total_out =Arith.add(Arith.div(price,OUT_RATIO,4),total_out);
            }
            //当前一口价
            Double  arith_buyoutPrice =Arith.mul(price,persentRatio,4);
            //当前的一口价大于传入的一口价的时候
            if (arith_buyoutPrice>buyoutPrice){
                 buyoutPrice =arith_buyoutPrice;
             }
            //竞价的收益
            double biddingGot = Arith.sub(Arith.mul(price, GOT_RATIO,4), total_out);
             //一口价的收益
            double oneGot =  Arith.sub(Arith.mul(buyoutPrice,GOT_RATIO,4),total_out) ;

            BiddingPredictio biddingPredictio =new BiddingPredictio();
            biddingPredictio.setNum(String.valueOf(i+1));
            biddingPredictio.setCurrentPrice(getPrice(price));
            biddingPredictio.setBuyout_price(getPrice(buyoutPrice));
            biddingPredictio.setSellerGotPrice(getPrice(biddingGot));
            biddingPredictio.setSellerGotBuyOutPrice(getPrice(oneGot));
            biddingPredictios.add(biddingPredictio);

        }
        return  biddingPredictios ;
    }

    private String getPrice(Double price){
        return  "¥"+String.valueOf(Arith.round(price,2));
    }






}
