package org.baoshichain.guessgame.bean;

import java.util.List;

/**
 *  抽奖房间详情列表
 */
public class DrawLuckRoom {

    private int userId;

    private int token;

    private String username;

    List<LuckRoom> list;

    public static class LuckRoom{
        private int activityid;
        private String roomname;
        private double price;
        private String winrate;
        private int num;
        private String token;  //每次参与积分

        public String  getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getActivityid() {
            return activityid;
        }

        public void setActivityid(int activityid) {
            this.activityid = activityid;
        }

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getWinrate() {
            return winrate;
        }

        public void setWinrate(String winrate) {
            this.winrate = winrate;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
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

    public List<LuckRoom> getList() {
        return list;
    }

    public void setList(List<LuckRoom> list) {
        this.list = list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
