package com.example.dream.mygo;

/**
 * Created by Dream on 2017-11-19.
 */

public class RedateNotice {

    private int redateID;
    private String userID;
    private String redateText;
    private String date;

    public RedateNotice(int redateID, String userID, String redateText, String date) {
        this.redateID = redateID;
        this.userID = userID;
        this.redateText = redateText;
        this.date = date;
    }

    public int getRedateID() {
        return redateID;
    }

    public void setRedateID(int redateID) {
        this.redateID = redateID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRedateText() {
        return redateText;
    }

    public void setRedateText(String redateText) {
        this.redateText = redateText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
