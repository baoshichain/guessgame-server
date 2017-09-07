package org.baoshichain.guessgame.bean;

import java.util.List;

public class Userinfo {

    private int userid;
    private int token;
    private int joinnum;

    private List<ActivityofJoin> list;

    public static class ActivityofJoin{
        private String roomname;
        private int price;
        private String time;
        private int joinnum;
        private int activityid;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getJoinnum() {
            return joinnum;
        }

        public void setJoinnum(int joinnum) {
            this.joinnum = joinnum;
        }
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getJoinnum() {
        return joinnum;
    }

    public void setJoinnum(int joinnum) {
        this.joinnum = joinnum;
    }

    public List<ActivityofJoin> getList() {
        return list;
    }

    public void setList(List<ActivityofJoin> list) {
        this.list = list;
    }
}
