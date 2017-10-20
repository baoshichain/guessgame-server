package org.baoshichain.guessgame.bean;

/**
 * 抽奖结果
 */
public class DrawluckResult {

    private int userId;

    private int token;

    private String username;

    private String result;

    private String cardname;

    private String cardprice;

    private String carddes;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getCardprice() {
        return cardprice;
    }

    public void setCardprice(String cardprice) {
        this.cardprice = cardprice;
    }

    public String getCarddes() {
        return carddes;
    }

    public void setCarddes(String carddes) {
        this.carddes = carddes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
