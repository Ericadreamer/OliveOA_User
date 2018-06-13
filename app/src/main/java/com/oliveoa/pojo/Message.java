package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class Message {
    private String mid;
    private String seid;
    private String eid;
    private String msg;
    private int orderby;
    private long createtime;
    private long updatetime;

    public Message() {
    }

    public Message(String mid, String seid, String eid, String msg, int orderby, long createtime, long updatetime) {
        this.mid = mid;
        this.seid = seid;
        this.eid = eid;
        this.msg = msg;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid='" + mid + '\'' +
                ", seid='" + seid + '\'' +
                ", eid='" + eid + '\'' +
                ", msg='" + msg + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
