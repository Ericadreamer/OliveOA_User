package com.oliveoa.common;

import java.util.ArrayList;

public class BusinessTripApplicationHttpResponseObject<T>{
    private int status;
    private String msg;
    private T data;

    public BusinessTripApplicationHttpResponseObject() {
    }

    public BusinessTripApplicationHttpResponseObject(int status, String msg, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BusinessTripApplicationHttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
