package com.oliveoa.jsonbean;

import com.oliveoa.pojo.BusinessTripApplication;

import java.util.ArrayList;

public class BusinessTripApplicationJsonBean {
    private int status;
    private String msg;
    private ArrayList<BusinessTripApplication> data;

    public BusinessTripApplicationJsonBean() {
    }

    public BusinessTripApplicationJsonBean(int status, String msg, ArrayList<BusinessTripApplication> data) {
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

    public ArrayList<BusinessTripApplication> getData() {
        return data;
    }

    public void setData(ArrayList<BusinessTripApplication> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BusinessTripApplicationJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
