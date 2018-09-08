package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class FulltimeApplication implements Parcelable {
    private String faid;

    private String eid;

    private long begintime;

    private long endtime;

    private String personalSummary;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public FulltimeApplication(String faid, String eid, long begintime, long endtime, String personalSummary, Integer orderby, long createtime, long updatetime) {
        this.faid = faid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.personalSummary = personalSummary;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public FulltimeApplication() {
        super();
    }

    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid == null ? null : faid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public long getBegintime() {
        return begintime;
    }

    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getPersonalSummary() {
        return personalSummary;
    }

    public void setPersonalSummary(String personalSummary) {
        this.personalSummary = personalSummary == null ? null : personalSummary.trim();
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
        return "FulltimeApplication{" +
                "faid='" + faid + '\'' +
                ", eid='" + eid + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", personalSummary='" + personalSummary + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected FulltimeApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

            faid  = in.readString();
            eid  = in.readString();
             begintime = in.readLong();
             endtime = in.readLong();
             personalSummary = in.readString();


             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法

         public static final Creator<FulltimeApplication> CREATOR = new Creator<FulltimeApplication>() {
             @Override
             public FulltimeApplication createFromParcel(Parcel in) {
                 return new FulltimeApplication(in);
             }

             @Override
             public FulltimeApplication[] newArray(int size) {
                 return new FulltimeApplication[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(faid);
             parcel.writeString(eid);
             parcel.writeLong(begintime);
              parcel.writeLong(endtime);
             parcel.writeString(personalSummary);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<FulltimeApplication> getCREATOR() {
             return CREATOR;
         }
}