package com.runer.toumai.bean;

/**
 * Created by ruier on 2018/7/16.
 *
 * 竞价预测的实体类
 */

public class BiddingPredictio {

    //竞价的轮次
    private String num  ;

    //当前价
    private String currentPrice ;

    //成交价
    private String buyout_price ;

    //（竞价成交）卖家所得
    private String sellerGotPrice ;

    //（一口价成交）卖家所得
    private String sellerGotBuyOutPrice ;

    public BiddingPredictio() {
    }

    public BiddingPredictio(String num, String currentPrice, String buyout_price, String sellerGotPrice, String sellerGotBuyOutPrice) {
        this.num = num;
        this.currentPrice = currentPrice;
        this.buyout_price = buyout_price;
        this.sellerGotPrice = sellerGotPrice;
        this.sellerGotBuyOutPrice = sellerGotBuyOutPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getBuyout_price() {
        return buyout_price;
    }

    public void setBuyout_price(String buyout_price) {
        this.buyout_price = buyout_price;
    }

    public String getSellerGotPrice() {
        return sellerGotPrice;
    }

    public void setSellerGotPrice(String sellerGotPrice) {
        this.sellerGotPrice = sellerGotPrice;
    }

    public String getSellerGotBuyOutPrice() {
        return sellerGotBuyOutPrice;
    }

    public void setSellerGotBuyOutPrice(String sellerGotBuyOutPrice) {
        this.sellerGotBuyOutPrice = sellerGotBuyOutPrice;
    }
}
