package org.baoshichain.guessgame.bean;

/**
 * Created by think on 2017-09-06.
 */
public class EthRoom {
    private String activityId;
    private String activityName;
    private String startBlock;
    private String endBlock;
    private String nowBlock;
    private String cardPrice;
    private String activityMembersNum;
    private String describe;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(String startBlock) {
        this.startBlock = startBlock;
    }

    public String getEndBlock() {
        return endBlock;
    }

    public void setEndBlock(String endBlock) {
        this.endBlock = endBlock;
    }

    public String getNowBlock() {
        return nowBlock;
    }

    public void setNowBlock(String nowBlock) {
        this.nowBlock = nowBlock;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getActivityMembersNum() {
        return activityMembersNum;
    }

    public void setActivityMembersNum(String activityMembersNum) {
        this.activityMembersNum = activityMembersNum;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "EthRoom{" +
                "activityId='" + activityId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", startBlock='" + startBlock + '\'' +
                ", endBlock='" + endBlock + '\'' +
                ", nowBlock='" + nowBlock + '\'' +
                ", cardPrice='" + cardPrice + '\'' +
                ", activityMembersNum='" + activityMembersNum + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}