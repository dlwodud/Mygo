package com.example.dream.mygo;

/**
 * Created by Dream on 2017-11-19.
 */

public class UserID {
    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserID.userID = userID;
    }

    private static String userID;
}

