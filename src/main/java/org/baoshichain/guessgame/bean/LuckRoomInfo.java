package org.baoshichain.guessgame.bean;

/**
 *  抽奖房间 详情页
 */
public class LuckRoomInfo {
    private int userId;
    private int token;
    private String username;

    private String roomname;
    private String roomprice;
    private String rate;
    private int num;
    private String des;
    private String evertoken; //每次抽奖积分
    private String joinnum; //剩余抽奖次数
    private String endstock;  //房间结束时间

    public String getEndstock() {
        return endstock;
    }

    public void setEndstock(String endstock) {
        this.endstock = endstock;
    }

    public String getJoinnum() {
        return joinnum;
    }

    public void setJoinnum(String joinnum) {
        this.joinnum = joinnum;
    }

    public String getEvertoken() {
        return evertoken;
    }

    public void setEvertoken(String evertoken) {
        this.evertoken = evertoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
