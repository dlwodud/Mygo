package com.example.dream.mygo;

/**
 * Created by Dream on 2017-11-21.
 */
public class Gender {
    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        Gender.gender = gender;
    }

    private static String gender;
}