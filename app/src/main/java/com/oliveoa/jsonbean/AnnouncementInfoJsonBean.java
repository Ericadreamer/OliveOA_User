package com.oliveoa.jsonbean;

import com.oliveoa.pojo.AnnouncementInfo;

import java.util.ArrayList;

public class AnnouncementInfoJsonBean {
    private int status;
    private ArrayList<AnnouncementInfo> data;

    public AnnouncementInfoJsonBean() {
    }

    public AnnouncementInfoJsonBean(int status, ArrayList<AnnouncementInfo> data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<AnnouncementInfo> getData() {
        return data;
    }

    public void setData(ArrayList<AnnouncementInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AnnouncementInfoJsonBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
