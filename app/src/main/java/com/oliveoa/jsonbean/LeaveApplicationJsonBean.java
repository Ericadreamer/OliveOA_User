package com.oliveoa.jsonbean;

import com.oliveoa.pojo.LeaveApplication;

import java.util.ArrayList;

public class LeaveApplicationJsonBean {
    private int status;
    private String msg;
    private ArrayList<LeaveApplication> data;

    public LeaveApplicationJsonBean() {
    }

    public LeaveApplicationJsonBean(int status, String msg, ArrayList<LeaveApplication> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<LeaveApplication> getData() {
        return data;
    }

    public void setData(ArrayList<LeaveApplication> data) {
        this.data = data;
    }
}

