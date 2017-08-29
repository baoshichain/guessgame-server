package org.baoshichain.guessgame.entity;

public class Activity {
    private Integer id;

    private Integer userid;

    private String describe;

    private String type;

    private Integer flag;

    private Integer startblock;

    private Integer endblock;

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
}