package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class BusinessTripApplication {
    private String btaid;
    private String eid;
    private long begintime;
    private long endtime;
    private String place;
    private String task;
    private int orderby;
    private long createime;
    private long updatetime;

    public BusinessTripApplication() {
    }

    public BusinessTripApplication(String btaid, String eid, long begintime, long endtime, String place, String task, int orderby, long createime, long updatetime) {
        this.btaid = btaid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.place = place;
        this.task = task;
        this.orderby = orderby;
        this.createime = createime;
        this.updatetime = updatetime;
    }

    public String getBtaid() {
        return btaid;
    }

    public void setBtaid(String btaid) {
        this.btaid = btaid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public long getBegintime() {
        return begintime;
    }

    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public long getCreateime() {
        return createime;
    }

    public void setCreateime(long createime) {
        this.createime = createime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "BusinessTripApplication{" +
                "btaid='" + btaid + '\'' +
                ", eid='" + eid + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", place='" + place + '\'' +
                ", task='" + task + '\'' +
                ", orderby=" + orderby +
                ", createime=" + createime +
                ", updatetime=" + updatetime +
                '}';
    }
}

