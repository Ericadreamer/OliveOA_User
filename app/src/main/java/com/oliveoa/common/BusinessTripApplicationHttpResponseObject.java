package com.oliveoa.common;

import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;

import java.util.ArrayList;

public class BusinessTripApplicationHttpResponseObject{
    private int status;
    private String msg;
    private BusinessTripApplicationInfoJsonBean data;

    public BusinessTripApplicationHttpResponseObject() {
    }

    public BusinessTripApplicationHttpResponseObject(int status, String msg, BusinessTripApplicationInfoJsonBean data) {
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

    public BusinessTripApplicationInfoJsonBean getData() {
        return data;
    }

    public void setData(BusinessTripApplicationInfoJsonBean data) {
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
