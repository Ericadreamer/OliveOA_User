package com.oliveoa.jsonbean;

import com.oliveoa.pojo.OvertimeApplication;

import java.util.ArrayList;

public class OvertimeApplicationInfoJsonBean {
    private int status;
    private String msg;
    private ArrayList<OvertimeApplication> data;

    public OvertimeApplicationInfoJsonBean() {
    }

    public OvertimeApplicationInfoJsonBean(int status, String msg, ArrayList<OvertimeApplication> data) {
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

    public ArrayList<OvertimeApplication> getData() {
        return data;
    }

    public void setData(ArrayList<OvertimeApplication> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OvertimeApplicationInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
