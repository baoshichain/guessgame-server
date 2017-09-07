package org.baoshichain.guessgame.bean;

/**
 * Created by think on 2017-08-31.
 */
public class EthActivity {

    private String activityName;
    private String cardName;
    private String cardDescribe;
    private String cardPrice;
    private String token;
    private String limitmax;
    private String limitmin;
    private String blockTime;
    private String activityDescribe;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescribe() {
        return cardDescribe;
    }

    public void setCardDescribe(String cardDescribe) {
        this.cardDescribe = cardDescribe;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLimitmax() {
        return limitmax;
    }

    public void setLimitmax(String limitmax) {
        this.limitmax = limitmax;
    }

    public String getLimitmin() {
        return limitmin;
    }

    public void setLimitmin(String limitmin) {
        this.limitmin = limitmin;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }

    public String getActivityDescribe() {
        return activityDescribe;
    }

    public void setActivityDescribe(String activityDescribe) {
        this.activityDescribe = activityDescribe;
    }


    @Override
    public String toString() {
        return "ethActivity{" +
                "activityName='" + activityName + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardDescribe='" + cardDescribe + '\'' +
                ", cardPrice='" + cardPrice + '\'' +
                ", token='" + token + '\'' +
                ", limitmax='" + limitmax + '\'' +
                ", limitmin='" + limitmin + '\'' +
                ", blockTime='" + blockTime + '\'' +
                ", activityDescribe='" + activityDescribe + '\'' +
                '}';
    }
/**
         "activityName":$("#activityName").val(),
         "cardName":$("#cardName").val(),
         "cardDescribe":$("#cardDescribe").val(),
         "cardPrice":$("#cardPrice").val(),
         "token":$("#token").val(),
         "limitmax":$("#limitmax").val(),
         "limitmin":$("#limitmin").val(),
         "blockTime":$("#blockTime").val(),
         "activityDescribe":$("#activityDescribe").val()
 */


}
