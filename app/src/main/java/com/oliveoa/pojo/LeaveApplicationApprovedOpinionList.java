package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class LeaveApplicationApprovedOpinionList implements Parcelable{
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

     //创建带参Parcel构造器
         protected LeaveApplicationApprovedOpinionList(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             laaocid = in.readString();
             laaopid = in.readString();
             laid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
             opinion = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<LeaveApplicationApprovedOpinionList> CREATOR = new Creator<LeaveApplicationApprovedOpinionList>() {
             @Override
             public LeaveApplicationApprovedOpinionList createFromParcel(Parcel in) {
                 return new LeaveApplicationApprovedOpinionList(in);
             }

             @Override
             public LeaveApplicationApprovedOpinionList[] newArray(int size) {
                 return new LeaveApplicationApprovedOpinionList[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(laaocid);
             parcel.writeString(laaopid);
             parcel.writeString(laid);
             parcel.writeString(eid);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<LeaveApplicationApprovedOpinionList> getCREATOR() {
             return CREATOR;
         }
}
