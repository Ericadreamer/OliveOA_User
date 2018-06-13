package com.oliveoa.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class LeaveApplicationApprovedOpinionList {
    private String laaocid;
    private String laaopid;
    private String laid;
    private String eid;
    private int isapproved;
    private String opinion;
    private int orderby;
    private long createtime;
    private long updatetime;

    public LeaveApplicationApprovedOpinionList() {
    }

    public LeaveApplicationApprovedOpinionList(String laaocid, String laaopid, String laid, String eid, int isapproved, String opinion, int orderby, long createtime, long updatetime) {
        this.laaocid = laaocid;
        this.laaopid = laaopid;
        this.laid = laid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getLaaocid() {
        return laaocid;
    }

    public void setLaaocid(String laaocid) {
        this.laaocid = laaocid;
    }

    public String getLaaopid() {
        return laaopid;
    }

    public void setLaaopid(String laaopid) {
        this.laaopid = laaopid;
    }

    public String getLaid() {
        return laid;
    }

    public void setLaid(String laid) {
        this.laid = laid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public int getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(int isapproved) {
        this.isapproved = isapproved;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
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
        return "LeaveApplicationApprovedOpinionList{" +
                "laaocid='" + laaocid + '\'' +
                ", laaopid='" + laaopid + '\'' +
                ", laid='" + laid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
