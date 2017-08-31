package org.baoshichain.guessgame.entity;

public class Activity {
    private Integer id;

    private Integer userid;

    private String activityname;

    private String describe;

    private String type;

    private Integer flag;

    private Integer startblock;

    private Integer endblock;

    private String token;

    private String limitmax;

    private String limitmin;

    private String winrate;

    private String nummax;

    private String num;

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

    public Integer getStartblock() {
        return startblock;
    }

    public void setStartblock(Integer startblock) {
        this.startblock = startblock;
    }

    public Integer getEndblock() {
        return endblock;
    }

    public void setEndblock(Integer endblock) {
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