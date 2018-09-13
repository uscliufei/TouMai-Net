package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/8/23/023.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * RulesDetailBean
 */

public class RulesDetailBean {


    /**
     * id : 5
     * title : 实款竞价
     * content : <p>
     问：为什么要“实款竞价“？
     </p>
     <p>
     答：”实款竞价“对买卖双方都有好处，而且线上支付的发展让”实款竞价“并不麻烦。<br />
     对买方：使”有偿出局“能够高校稳定运行。相较”保证金+瞎喊价“模式占用资金金额更少，时间更短，资金效率更高；<br />
     对卖方：相较”保证金+瞎喊价“模式，悔拍率大大降低，成交率大大提高——主要有人出价就确定卖出去了，再多的都是多赚的。
     </p>
     * article_type : 2
     * create_time : 2017-08-21 00:00:00
     * error : 0
     * msg : 请求成功
     */

    private String id;
    private String title;
    private String content;
    private String article_type;
    private String create_time;
    private String error;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticle_type() {
        return article_type;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
