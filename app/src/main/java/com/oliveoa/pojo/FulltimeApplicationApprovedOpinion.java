package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class FulltimeApplicationApprovedOpinion implements Parcelable{
    private String faaocid;

    private String faaopid;

    private String faid;

    private String eid;

    private Integer isapproved;

    private String opinion;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public FulltimeApplicationApprovedOpinion() {
    }

    public FulltimeApplicationApprovedOpinion(String faaocid, String faaopid, String faid, String eid, Integer isapproved, String opinion, Integer orderby, long createtime, long updatetime) {
        this.faaocid = faaocid;
        this.faaopid = faaopid;
        this.faid = faid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }


    public String getFaaocid() {
        return faaocid;
    }

    public void setFaaocid(String faaocid) {
        this.faaocid = faaocid;
    }

    public String getFaaopid() {
        return faaopid;
    }

    public void setFaaopid(String faaopid) {
        this.faaopid = faaopid;
    }

    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public Integer getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(Integer isapproved) {
        this.isapproved = isapproved;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
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
        return "FulltimeApplicationApprovedOpinion{" +
                "faaocid='" + faaocid + '\'' +
                ", faaopid='" + faaopid + '\'' +
                ", faid='" + faid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

    //创建带参Parcel构造器
    protected FulltimeApplicationApprovedOpinion(Parcel in) {
        //这里read字段的顺序要与write的顺序一致

        faaocid = in.readString();
        faaopid = in.readString();
        faid = in.readString();
        eid = in.readString();
        isapproved = in.readInt();
        opinion = in.readString();

        orderby = in.readInt();
        createtime = in.readLong();
        updatetime = in.readLong();
    }

    //创建常量Creator，并实现该接口的两个方法
    public static final Creator<FulltimeApplicationApprovedOpinion> CREATOR = new Creator<FulltimeApplicationApprovedOpinion>() {
        @Override
        public FulltimeApplicationApprovedOpinion createFromParcel(Parcel in) {
            return new FulltimeApplicationApprovedOpinion(in);
        }

        @Override
        public FulltimeApplicationApprovedOpinion[] newArray(int size) {
            return new FulltimeApplicationApprovedOpinion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(faaocid);
        parcel.writeString(faaopid);
        parcel.writeString(faid);
        parcel.writeString(eid);
        parcel.writeInt(isapproved);
        parcel.writeString(opinion);
        parcel.writeInt(orderby);
        parcel.writeLong(createtime);
        parcel.writeLong(updatetime);
    }

    public static Creator<FulltimeApplicationApprovedOpinion> getCREATOR() {
        return CREATOR;
    }
}