package com.example.callforhelpdemu;

public class Feedback {

    private String msg;
    private String rating;

    Feedback()
    {

    }

    public Feedback(String msg,String rating)
    {
        this.msg=msg;
        this.rating=rating;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
