package org.baoshichain.guessgame.bean;

/**
 * 抽奖结果
 */
public class DrawluckResult {

    private int userId;

    private int token;

    private String result;

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