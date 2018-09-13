package com.runer.net;

/**
 * http请求编号，由于像登录这种接口，可能会在不同的地方调用，
 * 因此需要设置独特的编号，已防止与其他编号冲突,独特编号从100开始
 * Create by szhua 2016/3/11
 */
public class RequestCode {

    public final static int CODE_0 = 0;
    public final static int CODE_1 = 1;
    public final static int CODE_2 = 2;
    public final static int CODE_3 = 3;
    public final static int CODE_4 = 4;
    public final static int CODE_5 =5 ;
    public final static int CODE_6= 6;
    public final static int CODE_SUCCESS =0;
    public final static  int SEND_CODE =11 ;
    public final static  int LOGIN=12 ;
    public final static  int REGISTER =14 ;
    public final static  int GET_USER_INFO =15 ;
    public final static  int UPDATE_HEADER =13 ;
    public static  final  int UPDATE_USER_UINFO =16 ;
    public static  final int CHANGE_PASS =17 ;
    public static  final int ADD_ZAN =18 ;
    public static  final int ADD_FAV =19 ;
    public static  final int DEL_FAV =20 ;
    public static  final int GET_BANNER =21 ;
    public static  final int  PHONE_AVLID =22;
    public static  final int LOADMORE =23 ;
    public static final int GET_GOODS_LIST= 24 ;
    public static  final int DEL_MSG =27;
    public static final int QUIE_CONCERN =25 ;
    public static  final int ADD_CONCERN =26 ;
    public static  final int ADD_OFFER =27 ;
    public static  final int ADD_AUTO_OFFER =28;
    public static  final int ADD_DARK =29 ;
    public  static  final int TOTAL_AUTO_OFFER =30 ;
    public static  final int GOODS_NOW_PRICE =31 ;
    public static  final int STOP_GOOD =32 ;
    public static  final int APPEAL_CREATE =33 ;
    public static final int APPEAL_UPLOAD =34 ;
    public static  final  int APPEAL_INFO =35 ;

    public static  final int ORDER_GET =36 ;
    public static  final int EXPRESS_LIST =37 ;
    public static  final int ORDER_PAY =38 ;
    public static  final int THIRD_LOGIN =39 ;
    public static  final  int WITH_DRAW =40 ;
    public static  final int GET_PAY_INFO =41 ;
    public static  final int BIND_PHONE =42 ;
    public static  final int VALID =43 ;
    public static final   int AliPay =44 ;
    public static  final int ALIOpen =45 ;

    public static  final int sys_read =46 ;

    public static  final int Msg_num =47 ;

    public static  final int orderAddress =48 ;

    public static  final int WEchatPay =49 ;

    public static  final int DELETE_OPEN =50 ;

    public static  final int FIRST_GET_MSG_NUM =51 ;


    public static  final int ADD_BUYOUT =52 ;

    public static  final int reg =53 ;

    public static  final int email_code_add =54 ;

    public static  final int user_login1 =55 ;

    public static  final int eidtEmail =56 ;

    public static  final int bind_email =57 ;

    public static  final int emailReg =58 ;

    public static  final int editPass =59 ;


}