package org.baoshichain.guessgame.bean;

/**
 *  抽奖房间 详情页
 */
public class LuckRoomInfo {
    private int userId;
    private int token;

    private String roomname;
    private String roomprice;
    private String rate;
    private int num;
    private String des;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomprice() {
        return roomprice;
    }

    public void setRoomprice(String roomprice) {
        this.roomprice = roomprice;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
