package com.oliveoa.jsonbean;

import com.oliveoa.pojo.Message;

import java.util.ArrayList;

public class MessageJsonbean {
    private int status;
    private String msg;
    private ArrayList<Message> data;

    public MessageJsonbean() {
    }

    public MessageJsonbean(int status, String msg, ArrayList<Message> data) {
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

    public ArrayList<Message> getData() {
        return data;
    }

    public void setData(ArrayList<Message> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageJsonbean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
