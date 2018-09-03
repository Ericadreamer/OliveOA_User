package com.oliveoa.jsonbean;

public class AnnouncementJsonBean {
    private int status;
    private AnnouncementDataJsonBean data;

    public AnnouncementJsonBean() {
    }

    public AnnouncementJsonBean(int status, AnnouncementDataJsonBean data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AnnouncementDataJsonBean getData() {
        return data;
    }

    public void setData(AnnouncementDataJsonBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AnnouncementJsonBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
