package com.oliveoa.common;

import com.oliveoa.jsonbean.ContactJsonBean;

import java.util.ArrayList;

public class ContactHttpResponseObject {
    private int status;
    private String msg;
    private ArrayList<ContactJsonBean> data;

    public ContactHttpResponseObject() {
    }

    public ContactHttpResponseObject(int status, String msg, ArrayList<ContactJsonBean> data) {
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

    public ArrayList<ContactJsonBean> getData() {
        return data;
    }

    public void setData(ArrayList<ContactJsonBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ContactHttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

