package org.baoshichain.guessgame.entity;

public class ActivityOfCard {
    private Integer id;

    private Integer activityid;  // 房间id

    private Integer cardid; //卡牌id

    private Integer flag;  //标识房间当前卡牌是否抽奖后 ，已中奖发出 0 未  1 发出

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public Integer getCardid() {
        return cardid;
    }

    public void setCardid(Integer cardid) {
        this.cardid = cardid;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}