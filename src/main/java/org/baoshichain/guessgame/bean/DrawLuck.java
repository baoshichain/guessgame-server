package org.baoshichain.guessgame.bean;

import java.util.List;

public class DrawLuck {

    private String activityname;


    private String token;

    private String probability;

    private String cardnum;

    private String limittime;

    private String roomdes;
    private String cardname;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getLimittime() {
        return limittime;
    }

    public void setLimittime(String limittime) {
        this.limittime = limittime;
    }

    public String getRoomdes() {
        return roomdes;
    }

    public void setRoomdes(String roomdes) {
        this.roomdes = roomdes;
    }
}
