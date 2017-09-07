package org.baoshichain.guessgame.entity;

public class UserOfActivity {
    private Integer id;

<<<<<<< HEAD
    private Integer userid; //用户id

    private Integer activityid; // 用户参与房间
=======
    private Integer userid;

    private Integer activityid;
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3

    private String flag;

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

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }
}