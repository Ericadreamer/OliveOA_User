package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

/**
 *  我的申请审批流程,上级意见结果
 *  OvertimeApplicationApprovedOpinionList
 */
@Entity
@Keep
public class OvertimeApplicationApprovedOpinionList implements Parcelable{
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

     //创建带参Parcel构造器
         protected OvertimeApplicationApprovedOpinionList(Parcel in) {
             //这里read字段的顺序要与write的顺序一致
             oaaocid = in.readString();
             oaaopid = in.readString();
             oaid = in.readString();
             eid = in.readString();
             isapproved = in.readInt();
             opinion = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<OvertimeApplicationApprovedOpinionList> CREATOR = new Creator<OvertimeApplicationApprovedOpinionList>() {
             @Override
             public OvertimeApplicationApprovedOpinionList createFromParcel(Parcel in) {
                 return new OvertimeApplicationApprovedOpinionList(in);
             }

             @Override
             public OvertimeApplicationApprovedOpinionList[] newArray(int size) {
                 return new OvertimeApplicationApprovedOpinionList[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(oaaocid);
             parcel.writeString(oaaopid);
             parcel.writeString(oaid);
             parcel.writeString(eid);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);


             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<OvertimeApplicationApprovedOpinionList> getCREATOR() {
             return CREATOR;
         }
}
