package com.runer.toumai.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by szhua on 2017/8/31/031.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * LogisticalResultBean
 */

public class LogisticalResultBean {
    /**
     * EBusinessID : 1289711
     * ShipperCode : HHTT
     * Success : true
     * LogisticCode : 667771430898
     * State : 3
     * Traces : [{"AcceptTime":"2017-07-27 17:44:07","AcceptStation":"【收件】上海新时代营业厅的刘刚18217192572已收件，扫描员是刘刚18217192572"},{"AcceptTime":"2017-07-29 20:57:47","AcceptStation":"【到件】快件到达上海集散航空组，上一站是无，扫描员是021998"},{"AcceptTime":"2017-07-30 00:31:55","AcceptStation":"【装袋】上海集散航空组已进行装袋扫描"},{"AcceptTime":"2017-07-30 00:31:56","AcceptStation":"【发件】快件由上海集散航空组发往济南分拨中心"},{"AcceptTime":"2017-07-30 00:52:08","AcceptStation":"【到件】快件到达上海集散航空组，上一站是无，扫描员是字建华"},{"AcceptTime":"2017-07-30 00:53:44","AcceptStation":"【发件】快件由上海集散航空组发往济南分拨中心"},{"AcceptTime":"2017-07-30 16:08:09","AcceptStation":"【到件】快件到达济南分拨中心，上一站是上海集散航空组，扫描员是陈晓凤"},{"AcceptTime":"2017-08-01 18:31:36","AcceptStation":"【发件】快件由济南分拨中心发往堤口路分部(0531-58788217)"},{"AcceptTime":"2017-08-02 08:00:00","AcceptStation":"【到件】快件到达堤口路分部(0531-58788217)，上一站是济南分拨中心，扫描员是陈广军15098807675"},{"AcceptTime":"2017-08-02 08:23:45","AcceptStation":"【派件】堤口路分部(0531-58788217)的陈清重13386408369正在派件"},{"AcceptTime":"2017-08-02 08:59:42","AcceptStation":"【签收】快件已签收,签收人是菜鸟驿站，签收网点是堤口路分部(0531-58788217)"},{"AcceptTime":"2017-08-02 09:04:01","AcceptStation":"【入柜】快件已由济南金色阳光花园小区店菜鸟驿站代收，请及时取件，如有疑问请联系15866721633"},{"AcceptTime":"2017-08-06 19:01:52","AcceptStation":"【用户提货】已签收，签收人凭取货码签收。"}]
     */
    @JsonProperty("EBusinessID")
    private String EBusinessID;
    @JsonProperty("ShipperCode")
    private String ShipperCode;
    @JsonProperty("Success")
    private boolean Success;
    @JsonProperty("LogisticCode")
    private String LogisticCode;
    @JsonProperty("State")
    private String State;
    @JsonProperty("Traces")
    private List<LogisticalBean> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }
    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public List<LogisticalBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<LogisticalBean> Traces) {
        this.Traces = Traces;
    }


}
