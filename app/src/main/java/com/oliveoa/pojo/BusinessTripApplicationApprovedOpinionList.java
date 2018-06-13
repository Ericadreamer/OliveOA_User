package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class BusinessTripApplicationApprovedOpinionList {
    private String btaaocid;
    private String btaaopid;
    private String btaid;
    private String eid;
    private int isapproved;
    private String opinion;
    private int orderby;
    private long createtime;
    private long updatetime;

    public BusinessTripApplicationApprovedOpinionList() {
    }

    public BusinessTripApplicationApprovedOpinionList(String btaaocid, String btaaopid, String btaid, String eid, int isapproved, String opinion, int orderby, long createtime, long updatetime) {
        this.btaaocid = btaaocid;
        this.btaaopid = btaaopid;
        this.btaid = btaid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getBtaaocid() {
        return btaaocid;
    }

    public void setBtaaocid(String btaaocid) {
        this.btaaocid = btaaocid;
    }

    public String getBtaaopid() {
        return btaaopid;
    }

    public void setBtaaopid(String btaaopid) {
        this.btaaopid = btaaopid;
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
        return "BusinessTripApplicationApprovedOpinionList{" +
                "btaaocid='" + btaaocid + '\'' +
                ", btaaopid='" + btaaopid + '\'' +
                ", btaid='" + btaid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
