package com.example.callforhelpdemu;

import android.widget.Spinner;

public class Informetion {

    private String fullname;
    private String phoneno;
    private  String UserId;
    private Spinner spinner;


    public Informetion(String fullname, String phoneno, String userId) {
        this.fullname = fullname;
        this.phoneno = phoneno;
        UserId = userId;
        //this.spinner=spinner;
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


}
