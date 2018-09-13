package com.runer.toumai.util;

import android.content.Context;

import com.runer.toumai.R;
import com.runer.toumai.bean.FaqListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/9/12/012.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * FaqUtil
 */

public class FaqUtil {


    /* 为什么“暗价”功能需要完成任务才能解锁？
“暗价”是什么意思？
“暗价”阶段为什么不能“加一手出价”？
“暗价”阶段中“大循环式”竞价具体是怎么运行的？
“暗价”的意义是什么？

“明价”是什么意思？

“降价”是什么意思？
“降价”是怎么运行的？
“降价节奏”为什么要统一规定？
“降价”阶段为什么只能“全额支付”而不能“溢价支付”？
“降价”的意义是什么？

“心跳时间”是什么意思？
“心跳时间”的意义是什么？







*/
    public static List<FaqListBean> getOrderStateFaq(Context context){
        List<FaqListBean> faqListBeans =new ArrayList<>();
        FaqListBean faqListBean1 =new FaqListBean() ;
        faqListBean1.setContent("为什么“暗价”功能需要完成任务才能解锁？");
        faqListBean1.setValue("10");
        faqListBeans.add(faqListBean1);
        FaqListBean faqListBean2 =new FaqListBean() ;
        faqListBean2.setContent("“暗价”是什么意思？");
        faqListBean2.setValue("11");
        faqListBeans.add(faqListBean2) ;
        FaqListBean faqListBean3 =new FaqListBean() ;
        faqListBean3.setContent("“暗价”阶段为什么不能“加一手出价”？");
        faqListBean3.setValue("12");
        faqListBeans.add(faqListBean3);
        FaqListBean faqListBean5 =new FaqListBean() ;
        faqListBean5.setContent("“暗价”阶段中“大循环式”竞价具体是怎么运行的？");
        faqListBean5.setValue("13");
        faqListBeans.add(faqListBean5);
        FaqListBean faqListBean4 =new FaqListBean() ;
        faqListBean4.setContent("“暗价”的意义是什么？");
        faqListBean4.setValue("14");
        faqListBeans.add(faqListBean4);
        faqListBean4.setSpace(true);
        FaqListBean faqListBean6=new FaqListBean() ;
        faqListBean6.setContent("“明价”是什么意思？");
        faqListBean6.setValue("15");
        faqListBeans.add(faqListBean6);
        faqListBean6.setSpace(true);
        /*“降价”是什么意思？
“降价”是怎么运行的？
“降价节奏”为什么要统一规定？
“降价”阶段为什么只能“全额支付”而不能“溢价支付”？
“降价”的意义是什么？
*/
         FaqListBean faqListBean7 =new FaqListBean() ;
        faqListBean7.setContent("“降价”是什么意思？");
        faqListBean7.setValue("18");
        faqListBeans.add(faqListBean7) ;

        FaqListBean faqListBean8 =new FaqListBean() ;
        faqListBean8.setContent("“降价”是怎么运行的？");
        faqListBean8.setValue("19");
        faqListBeans.add(faqListBean8) ;

        FaqListBean faqListBean9=new FaqListBean() ;
        faqListBean9.setContent("“降价节奏”为什么要统一规定？");
        faqListBean9.setValue("20");
        faqListBeans.add(faqListBean9) ;

        FaqListBean faqListBean10=new FaqListBean() ;
        faqListBean10.setContent("“降价”阶段为什么只能“全额支付”而不能“溢价支付”？");
        faqListBean10.setValue("21");
        faqListBeans.add(faqListBean10) ;

        FaqListBean faqListBean11=new FaqListBean() ;
        faqListBean11.setContent("“降价”的意义是什么？");
        faqListBean11.setValue("22");
        faqListBeans.add(faqListBean11) ;
        faqListBean11.setSpace(true);

        /*“心跳时间”是什么意思？
“心跳时间”的意义是什么？
*/
        FaqListBean faqListBean12=new FaqListBean() ;
        faqListBean12.setContent("“心跳时间”是什么意思？");
        faqListBean12.setValue("16");
        faqListBeans.add(faqListBean12) ;


        FaqListBean faqListBean13=new FaqListBean() ;
        faqListBean13.setContent("“心跳时间”的意义是什么？");
        faqListBean13.setValue("17");
        faqListBeans.add(faqListBean13) ;
        faqListBean13.setSpace(true);

        return  faqListBeans ;

    }

    /*“底价”是什么意思？
“可接受的最低价”是什么意思？
*/
    public static List<FaqListBean> getInputPriceFaq(Context context){
        List<FaqListBean>  data = getOrderStateFaq(context);;
        data.add(0,new FaqListBean("“底价”是什么意思？","37",false));
        data.add(1,new FaqListBean("“可接受的最低价”是什么意思？","36",true));
        return  data ;

    }


    /*为什么要“实款竞价”？
为什么只能通过账户钱包付款而不能直接付款？

“加一手出价”是什么意思？
“托管出价”具体是怎么运行的？
“托管出价”的意义是什么？

每轮竞价涨幅（竞价阶梯）为什么定为当前价的1%？
出局买家可以再就同一商品多次出价么？
*/



    public static  List<FaqListBean> getChujiaFaq(Context context){
        List<FaqListBean> faqListBeans =new ArrayList<>() ;
        faqListBeans.add(new FaqListBean("为什么要“实款竞价”？","1",false));
        faqListBeans.add(new FaqListBean("为什么只能通过账户钱包付款而不能直接付款？","32",true));
        faqListBeans.add(new FaqListBean("“加一手出价”是什么意思？","6",false));
        faqListBeans.add(new FaqListBean("“托管出价”具体是怎么运行的？","7",false));
        faqListBeans.add(new FaqListBean("“托管出价”的意义是什么？","8",true));
        faqListBeans.add(new FaqListBean("每轮竞价涨幅（竞价阶梯）为什么定为当前价的1%？","4",false));
        faqListBeans.add(new FaqListBean("出局买家可以再就同一商品多次出价么？","5",false));
        return  faqListBeans ;
    }


    /*“有偿出局”是什么意思？
“有偿出局”的意义是什么？

每轮竞价涨幅（竞价阶梯）为什么定为当前价的1%？
出局买家可以再就同一商品多次出价么？
*/
    public static  List<FaqListBean> getOfferListFaq(Context context){
        List<FaqListBean> faqListBeans =new ArrayList<>() ;
        faqListBeans.add(new FaqListBean("“有偿出局”是什么意思？","2",false));
        faqListBeans.add(new FaqListBean("“有偿出局”的意义是什么？","3",true));
        faqListBeans.add(new FaqListBean("每轮竞价涨幅（竞价阶梯）为什么定为当前价的1%？","4",false));
        faqListBeans.add(new FaqListBean("出局买家可以再就同一商品多次出价么？","5",false));
        return  faqListBeans ;
    }


    /*竞价结束后的具体流程是什么？
溢价支付的买家不支付尾款怎么办？
买家付款后，卖家不发货怎么办？
卖家发货后，买家不确认收货怎么办？
买家收货后，发现卖家描述不实怎么办？
申诉有什么用？
为什么要以一轮竞价收益作为卖家的违约金数额？
为什么余额为负不能发布商品？
*/

    public static List<FaqListBean> getOrderListFaq(Context context){
        List<FaqListBean> faqListBeans =new ArrayList<>() ;
        faqListBeans.add(new FaqListBean("竞价结束后的具体流程是什么？","23",true));
        faqListBeans.add(new FaqListBean("溢价支付的买家不支付尾款怎么办？","24",false));
        faqListBeans.add(new FaqListBean("买家付款后，卖家不发货怎么办？","25",false));
        faqListBeans.add(new FaqListBean("卖家发货后，买家不确认收货怎么办？","26",false));
        faqListBeans.add(new FaqListBean("买家收货后，发现卖家描述不实怎么办？","27",false));
        faqListBeans.add(new FaqListBean("申诉有什么用？","28",false));
        faqListBeans.add(new FaqListBean("为什么要以一轮竞价收益作为卖家的违约金数额？","29",false));
        faqListBeans.add(new FaqListBean("为什么余额为负不能发布商品？","30",false));
        return  faqListBeans ;
    }
}
