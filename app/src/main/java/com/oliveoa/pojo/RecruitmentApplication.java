package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class RecruitmentApplication implements Parcelable{
    private String raid;

    private String dcid;

    private String eid;

    private int orderby;

    private long createtime;

    private long updatetime;

    public RecruitmentApplication(String raid, String dcid, String eid, int orderby, Long createtime, Long updatetime) {
        this.raid = raid;
        this.dcid = dcid;
        this.eid = eid;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public RecruitmentApplication() {
    }

    public String getRaid() {
        return raid;
    }

    public void setRaid(String raid) {
        this.raid = raid == null ? null : raid.trim();
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid == null ? null : dcid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
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
        return "RecruitmentApplication{" +
                "raid='" + raid + '\'' +
                ", dcid='" + dcid + '\'' +
                ", eid='" + eid + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected RecruitmentApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             raid = in.readString();
             dcid = in.readString();
             eid = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<RecruitmentApplication> CREATOR = new Creator<RecruitmentApplication>() {
             @Override
             public RecruitmentApplication createFromParcel(Parcel in) {
                 return new RecruitmentApplication(in);
             }

             @Override
             public RecruitmentApplication[] newArray(int size) {
                 return new RecruitmentApplication[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(raid);
             parcel.writeString(dcid);
             parcel.writeString(eid);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<RecruitmentApplication> getCREATOR() {
             return CREATOR;
         }
}