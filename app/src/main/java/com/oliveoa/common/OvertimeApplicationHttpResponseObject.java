package com.oliveoa.common;

import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;

public class OvertimeApplicationHttpResponseObject<T> {
    private int status;
    private String msg;
    private OvertimeApplicationJsonBean data;

    public OvertimeApplicationHttpResponseObject() {
    }

    public OvertimeApplicationHttpResponseObject(int status, String msg, OvertimeApplicationJsonBean data) {
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

    public OvertimeApplicationJsonBean getOvertimeApplicationJsonBean() {
        return data;
    }

    public void setOvertimeApplicationJsonBean(OvertimeApplicationJsonBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OvertimeApplicationHttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
