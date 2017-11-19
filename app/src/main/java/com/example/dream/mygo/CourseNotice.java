package com.example.dream.mygo;

/**
 * Created by Dream on 2017-11-17.
 */

public class CourseNotice {
    int courseID; //게시글 번호
    String userID; //게시자
    String courseTitle;  // 제목
    String courseStroy;  // 내용
    String courseDate1;  //  날짜
    int courseHit;      // 조회수
    String courseWorry;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
//

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStroy() {
        return courseStroy;
    }

    public void setCourseStroy(String courseStroy) {
        this.courseStroy = courseStroy;
    }

    public String getCourseDate1() {
        return courseDate1;
    }

    public void setCourseDate1(String courseDate1) {
        this.courseDate1 = courseDate1;
    }


    public int getCourseHit() {
        return courseHit;
    }

    public void setCourseHit(int courseHit) {
        this.courseHit = courseHit;
    }

    public String getCourseWorry() {
        return courseWorry;
    }

    public void setCourseWorry(String courseWorry) {
        this.courseWorry = courseWorry;
    }


    public CourseNotice(int courseID, String userID, String courseTitle, String courseStroy, String courseWorry, String courseDate1,int courseHit) {
        this.courseID = courseID;
        this.userID = userID;
        this.courseTitle = courseTitle;
        this.courseStroy = courseStroy;
        this.courseDate1 = courseDate1;
        this.courseHit = courseHit;
        this.courseWorry = courseWorry;
    }
}
