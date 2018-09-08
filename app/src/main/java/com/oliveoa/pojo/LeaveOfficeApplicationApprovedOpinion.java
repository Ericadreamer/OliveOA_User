package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class LeaveOfficeApplicationApprovedOpinion implements Parcelable{
    private String loaaocid;

    private String loaaopid;

    private String loaid;

    private String eid;

    private Integer isapproved;

    private String opinion;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public LeaveOfficeApplicationApprovedOpinion(String loaaocid, String loaaopid, String loaid, String eid, Integer isapproved, String opinion, Integer orderby, long createtime, long updatetime) {
        this.loaaocid = loaaocid;
        this.loaaopid = loaaopid;
        this.loaid = loaid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public LeaveOfficeApplicationApprovedOpinion() {
        super();
    }

    public String getLoaaocid() {
        return loaaocid;
    }

    public void setLoaaocid(String loaaocid) {
        this.loaaocid = loaaocid == null ? null : loaaocid.trim();
    }

    public String getLoaaopid() {
        return loaaopid;
    }

    public void setLoaaopid(String loaaopid) {
        this.loaaopid = loaaopid == null ? null : loaaopid.trim();
    }

    public String getLoaid() {
        return loaid;
    }

    public void setLoaid(String loaid) {
        this.loaid = loaid == null ? null : loaid.trim();
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
        return "LeaveOfficeApplicationApprovedOpinion{" +
                "loaaocid='" + loaaocid + '\'' +
                ", loaaopid='" + loaaopid + '\'' +
                ", loaid='" + loaid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected LeaveOfficeApplicationApprovedOpinion(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             loaaocid = in.readString();
             loaaopid = in.readString();
             loaid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
              opinion= in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<LeaveOfficeApplicationApprovedOpinion> CREATOR = new Creator<LeaveOfficeApplicationApprovedOpinion>() {
             @Override
             public LeaveOfficeApplicationApprovedOpinion createFromParcel(Parcel in) {
                 return new LeaveOfficeApplicationApprovedOpinion(in);
             }

             @Override
             public LeaveOfficeApplicationApprovedOpinion[] newArray(int size) {
                 return new LeaveOfficeApplicationApprovedOpinion[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(loaaocid);
             parcel.writeString(loaaopid);
             parcel.writeString(loaid);
             parcel.writeString(eid);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<LeaveOfficeApplicationApprovedOpinion> getCREATOR() {
             return CREATOR;
         }
}