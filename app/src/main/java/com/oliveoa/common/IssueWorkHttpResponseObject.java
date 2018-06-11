package com.oliveoa.common;

import com.oliveoa.jsonbean.IssueWorkJsonBean;

import java.util.ArrayList;

public class IssueWorkHttpResponseObject {
    private int status;
    private String msg;
    private ArrayList<IssueWorkJsonBean> data;

    public IssueWorkHttpResponseObject() {
    }

    public IssueWorkHttpResponseObject(int status, String msg, ArrayList<IssueWorkJsonBean> data) {
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

    public ArrayList<IssueWorkJsonBean> getData() {
        return data;
    }

    public void setData(ArrayList<IssueWorkJsonBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IssueWorkHttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
