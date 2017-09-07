package org.baoshichain.guessgame.bean;

import java.util.List;

public class RoomControll {

    private int userid;
    private int token;

    private int publishnum;

    private List<Wininfo> list;

    public static class Wininfo{
         private String activityname;
         private int userid;
         private String phone;
         private int flag;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getActivityname() {
            return activityname;
        }

        public void setActivityname(String activityname) {
            this.activityname = activityname;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
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

    public int getPublishnum() {
        return publishnum;
    }

    public void setPublishnum(int publishnum) {
        this.publishnum = publishnum;
    }

    public List<Wininfo> getList() {
        return list;
    }

    public void setList(List<Wininfo> list) {
        this.list = list;
    }
}
