package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class AnnouncementApprovedOpinionList implements Parcelable{
    private String aaocid;
    private String aaopid;
    private String aid;
    private String eid;
    private int isapproved;
    private String opinion;
    private int orderby;
    private long createtime;
    private long updatetime;

    public AnnouncementApprovedOpinionList() {
    }

    public AnnouncementApprovedOpinionList(String aaocid, String aaopid, String aid, String eid, int isapproved, String opinion, int orderby, long createtime, long updatetime) {
        this.aaocid = aaocid;
        this.aaopid = aaopid;
        this.aid = aid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getAaocid() {
        return aaocid;
    }

    public void setAaocid(String aaocid) {
        this.aaocid = aaocid;
    }

    public String getAaopid() {
        return aaopid;
    }

    public void setAaopid(String aaopid) {
        this.aaopid = aaopid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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
        return "AnnouncementApprovedOpinionList{" +
                "aaocid='" + aaocid + '\'' +
                ", aaopid='" + aaopid + '\'' +
                ", aid='" + aid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected AnnouncementApprovedOpinionList(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             aaocid = in.readString();
             aaopid = in.readString();
             aid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
             opinion = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<AnnouncementApprovedOpinionList> CREATOR = new Creator<AnnouncementApprovedOpinionList>() {
             @Override
             public AnnouncementApprovedOpinionList createFromParcel(Parcel in) {
                 return new AnnouncementApprovedOpinionList(in);
             }

             @Override
             public AnnouncementApprovedOpinionList[] newArray(int size) {
                 return new AnnouncementApprovedOpinionList[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(aaocid);
             parcel.writeString(aaopid);
             parcel.writeString(aid);
             parcel.writeString(eid);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);


             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<AnnouncementApprovedOpinionList> getCREATOR() {
             return CREATOR;
         }

}
