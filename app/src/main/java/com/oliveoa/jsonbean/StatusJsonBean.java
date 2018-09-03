package com.oliveoa.jsonbean;

public class StatusJsonBean {
    private int status;

    public StatusJsonBean(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusJsonBean{" +
                "status=" + status +
                '}';
    }
}
