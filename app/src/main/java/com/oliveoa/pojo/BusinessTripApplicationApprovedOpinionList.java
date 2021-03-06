package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class BusinessTripApplicationApprovedOpinionList implements Parcelable{
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

     //创建带参Parcel构造器
         protected BusinessTripApplicationApprovedOpinionList(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             btaaocid = in.readString();
             btaaopid = in.readString();
             btaid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
             opinion =in.readString();
             orderby = in.readInt();
             createtime = in.readLong();
             updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<BusinessTripApplicationApprovedOpinionList> CREATOR = new Creator<BusinessTripApplicationApprovedOpinionList>() {
             @Override
             public BusinessTripApplicationApprovedOpinionList createFromParcel(Parcel in) {
                 return new BusinessTripApplicationApprovedOpinionList(in);
             }

             @Override
             public BusinessTripApplicationApprovedOpinionList[] newArray(int size) {
                 return new BusinessTripApplicationApprovedOpinionList[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(btaaocid);
             parcel.writeString(btaaopid);
             parcel.writeString(btaid);
             parcel.writeString(eid);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<BusinessTripApplicationApprovedOpinionList> getCREATOR() {
             return CREATOR;
         }
}
