package com.example.callforhelpdemu;

import android.widget.Spinner;

public class Informetion {

    private String fullname;
    private String phoneno;
    private  String UserId;
    private String spinner;
    private  String date;


    public Informetion(String fullname, String phoneno, String userId, String spinner ,String date ) {

        this.fullname = fullname;
        this.phoneno = phoneno;
        UserId = userId;
        this.spinner = spinner;
        this.date = date;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
