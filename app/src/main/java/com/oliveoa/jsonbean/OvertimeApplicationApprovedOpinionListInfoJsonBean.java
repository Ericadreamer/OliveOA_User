package com.oliveoa.jsonbean;

import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;

import java.util.ArrayList;

public class OvertimeApplicationApprovedOpinionListInfoJsonBean {
    private int status;
    private String msg;
    private ArrayList<OvertimeApplicationApprovedOpinionList> data;

    public OvertimeApplicationApprovedOpinionListInfoJsonBean() {
    }

    public OvertimeApplicationApprovedOpinionListInfoJsonBean(int status, String msg, ArrayList<OvertimeApplicationApprovedOpinionList> data) {
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

    public ArrayList<OvertimeApplicationApprovedOpinionList> getData() {
        return data;
    }

    public void setData(ArrayList<OvertimeApplicationApprovedOpinionList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OvertimeApplicationApprovedOpinionListInfoJsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
