package com.oliveoa.pojo;

/**
 *  我的申请审批流程,上级意见结果
 *  OvertimeApplicationApprovedOpinionList
 */

public class OvertimeApplicationApprovedOpinionList {
    private String oaaocid;
    private String oaaopid;
    private String oaid;
    private String eid;
    private int isapproved;
    private String opinion;
    private int orderby;
    private long createtime;
    private long updatetime;

    public OvertimeApplicationApprovedOpinionList() {
    }

    public OvertimeApplicationApprovedOpinionList(String oaaocid, String oaaopid, String oaid, String eid, int isapproved, String opinion, int orderby, long createtime, long updatetime) {
        this.oaaocid = oaaocid;
        this.oaaopid = oaaopid;
        this.oaid = oaid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getOaaocid() {
        return oaaocid;
    }

    public void setOaaocid(String oaaocid) {
        this.oaaocid = oaaocid;
    }

    public String getOaaopid() {
        return oaaopid;
    }

    public void setOaaopid(String oaaopid) {
        this.oaaopid = oaaopid;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
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
        return "OvertimeApplicationApprovedOpinionList{" +
                "oaaocid='" + oaaocid + '\'' +
                ", oaaopid='" + oaaopid + '\'' +
                ", oaid='" + oaid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
