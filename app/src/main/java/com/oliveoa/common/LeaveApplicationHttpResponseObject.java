package com.oliveoa.common;

import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

public class LeaveApplicationHttpResponseObject {
    private int status;
    private String msg;
    private LeaveApplicationInfoJsonBean data;

    public LeaveApplicationHttpResponseObject() {
    }

    public LeaveApplicationHttpResponseObject(int status, String msg, LeaveApplicationInfoJsonBean data) {
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

    public LeaveApplicationInfoJsonBean getData() {
        return data;
    }

    public void setData(LeaveApplicationInfoJsonBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LeaveApplicationHttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
