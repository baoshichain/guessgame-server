package org.baoshichain.guessgame.entity;

public class Activity {
    private Integer id;

    private Integer userid;//庄家ID

    private String activityname;  //房间名称

    private String describe;//房间介绍

    private String type; //房间类型  1 开奖 2 抽奖

    private Integer flag;  //活动是否开始结束  0 已完成 1 活动中

    private String startblock; //起始时间

    private String endblock; //结束时间

    private String token; //每次参与所需积分

    private String limitmax;  //开奖上限

    private String limitmin; //最低开奖人数

    private String winrate;  //中奖率

    private String nummax; // 房间卡牌数目上限

    private String num; //房间 剩余卡牌数目


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname == null ? null : activityname.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getStartblock() {
        return startblock;
    }

    public void setStartblock(String startblock) {
        this.startblock = startblock;
    }

    public String getEndblock() {
        return endblock;
    }

    public void setEndblock(String endblock) {
        this.endblock = endblock;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getLimitmax() {
        return limitmax;
    }

    public void setLimitmax(String limitmax) {
        this.limitmax = limitmax == null ? null : limitmax.trim();
    }

    public String getLimitmin() {
        return limitmin;
    }

    public void setLimitmin(String limitmin) {
        this.limitmin = limitmin == null ? null : limitmin.trim();
    }

    public String getWinrate() {
        return winrate;
    }

    public void setWinrate(String winrate) {
        this.winrate = winrate == null ? null : winrate.trim();
    }

    public String getNummax() {
        return nummax;
    }

    public void setNummax(String nummax) {
        this.nummax = nummax == null ? null : nummax.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }
}