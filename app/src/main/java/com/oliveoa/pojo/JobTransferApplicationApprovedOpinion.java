package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class JobTransferApplicationApprovedOpinion implements Parcelable{
    private String jtaaocid;

    private String jtaaopid;

    private String jtaid;

    private String eid;

    private Integer isapproved;

    private String opinion;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public JobTransferApplicationApprovedOpinion(String jtaaocid, String jtaaopid, String jtaid, String eid, Integer isapproved, String opinion, Integer orderby, long createtime, long updatetime) {
        this.jtaaocid = jtaaocid;
        this.jtaaopid = jtaaopid;
        this.jtaid = jtaid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public JobTransferApplicationApprovedOpinion() {
        super();
    }

    public String getJtaaocid() {
        return jtaaocid;
    }

    public void setJtaaocid(String jtaaocid) {
        this.jtaaocid = jtaaocid == null ? null : jtaaocid.trim();
    }

    public String getJtaaopid() {
        return jtaaopid;
    }

    public void setJtaaopid(String jtaaopid) {
        this.jtaaopid = jtaaopid == null ? null : jtaaopid.trim();
    }

    public String getJtaid() {
        return jtaid;
    }

    public void setJtaid(String jtaid) {
        this.jtaid = jtaid == null ? null : jtaid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
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
        this.opinion = opinion == null ? null : opinion.trim();
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
        return "JobTransferApplicationApprovedOpinion{" +
                "jtaaocid='" + jtaaocid + '\'' +
                ", jtaaopid='" + jtaaopid + '\'' +
                ", jtaid='" + jtaid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

    //创建带参Parcel构造器
         protected JobTransferApplicationApprovedOpinion(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             jtaaocid = in.readString();
             jtaaopid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
             opinion = in.readString();
             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<JobTransferApplicationApprovedOpinion> CREATOR = new Creator<JobTransferApplicationApprovedOpinion>() {
             @Override
             public JobTransferApplicationApprovedOpinion createFromParcel(Parcel in) {
                 return new JobTransferApplicationApprovedOpinion(in);
             }

             @Override
             public JobTransferApplicationApprovedOpinion[] newArray(int size) {
                 return new JobTransferApplicationApprovedOpinion[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(jtaaocid);
             parcel.writeString(jtaaopid);
             parcel.writeString(eid);
              parcel.writeInt(isapproved);
             parcel.writeString(opinion);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<JobTransferApplicationApprovedOpinion> getCREATOR() {
             return CREATOR;
         }
}