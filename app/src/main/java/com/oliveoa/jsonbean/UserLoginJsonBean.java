package com.oliveoa.jsonbean;

import com.oliveoa.pojo.ContactInfo;

public class UserLoginJsonBean {
    private int status;
    private String msg;
    private ContactInfo data;

    public UserLoginJsonBean(int status, String msg, ContactInfo data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public ContactInfo getData() { return data; }
    public void setData(ContactInfo data) { this.data = data; }

    @Override
    public String toString() {
        return "UserLoginJsonBean{" +
                "status=" + status +
                ", msg="+ msg + '\'' +
                ", data=" + data +
                '}' ;
    }
}
