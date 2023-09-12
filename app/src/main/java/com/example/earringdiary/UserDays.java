package com.example.earringdiary;

public class UserDays {
    private String userday_title;
    private String userday_loc;
    private String userday_cmt;
    private String userday_startday;
    private String userday_endday;
    private String userday_starttime;
    private String userday_endtime;

    public UserDays(String userday_title, String userday_loc, String userday_cmt, String userday_startday, String userday_endday, String userday_starttime, String userday_endtime){
        this.userday_title = userday_title;
        this.userday_loc = userday_loc;
        this.userday_cmt = userday_cmt;
        this.userday_startday = userday_startday;
        this.userday_endday = userday_endday;
        this.userday_starttime = userday_starttime;
        this.userday_endtime = userday_endtime;
    }

    public String getUserDay_title(){
        return userday_title;
    }

    public void setUserDay_title(String userday_title){
        this.userday_title = userday_title;
    }

    public String getUserDay_loc(){
        return userday_loc;
    }

    public void setUserDay_loc(String userday_loc){
        this.userday_loc = userday_loc;
    }

    public String getUserDay_cmt(){
        return userday_cmt;
    }

    public void setUserDay_cmt(String userday_cmt){
        this.userday_cmt = userday_cmt;
    }

    public String getUserDay_sDay(){
        return userday_startday;
    }

    public void setUserDay_sDay(String userday_startday){
        this.userday_startday = userday_startday;
    }

    public String getUserDay_eDay(){
        return userday_endday;
    }

    public void setUserDay_eDay(String userday_endday){
        this.userday_endday = userday_endday;
    }

    public String getUserDay_sTime(){
        return userday_starttime;
    }

    public void setUserDay_sTime(String userday_starttime){
        this.userday_starttime = userday_starttime;
    }

    public String getUserDay_eTime(){
        return userday_endtime;
    }

    public void setUserDay_eTime(String userday_endtime){
        this.userday_endtime = userday_endtime;
    }



}
