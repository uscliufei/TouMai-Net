package com.runer.toumai.bean;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * MineListItemBean
 */

public class MineListItemBean {

    String title ;
    String rightText ;
    int img ;
    boolean isHasMsg ;

    public MineListItemBean(String title , String rightText , int img ){
        this.title =title ;
        this.rightText =rightText ;
        this.img =img ;
        this.isHasMsg =false ;
    }


    public MineListItemBean(String title , String rightText , int img  ,boolean isHasMsg){
        this.title =title ;
        this.rightText =rightText ;
        this.img =img ;
        this.isHasMsg =isHasMsg ;
    }


    public boolean isHasMsg() {
        return isHasMsg;
    }

    public void setHasMsg(boolean hasMsg) {
        isHasMsg = hasMsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
