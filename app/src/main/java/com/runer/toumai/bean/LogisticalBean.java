package com.runer.toumai.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by szhua on 2017/7/18/018.
 * github:https://github.com/szhua
 * TouMaiNetApp
 * LogisticalBean
 */

public class LogisticalBean {

    private boolean isFirst;
    private boolean isEnd;
    @JsonProperty("AcceptTime")
    String AcceptTime;
    @JsonProperty("AcceptStation")
String AcceptStation ;

    public String getAcceptTime(){
        return AcceptTime;
    }
    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
    public String getAcceptStation() {
        return AcceptStation;
    }
    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }
    @Override
    public String toString() {
        return "LogisticalBean{" +
                "isFirst=" + isFirst +
                ", isEnd=" + isEnd +
                ", AcceptTime='" + AcceptTime + '\'' +
                ", AcceptStation='" + AcceptStation + '\'' +
                '}';
    }
}
