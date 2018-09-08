package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class JobTransferApplication implements Parcelable{
    private String jtaid;

    private String aeid;

    private String eid;

    private String aimdcid;

    private String aimpcid;

    private String reason;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public JobTransferApplication(String jtaid, String aeid, String eid, String aimdcid, String aimpcid, String reason, Integer orderby, long createtime, long updatetime) {
        this.jtaid = jtaid;
        this.aeid = aeid;
        this.eid = eid;
        this.aimdcid = aimdcid;
        this.aimpcid = aimpcid;
        this.reason = reason;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public JobTransferApplication() {
        super();
    }

    public String getJtaid() {
        return jtaid;
    }

    public void setJtaid(String jtaid) {
        this.jtaid = jtaid == null ? null : jtaid.trim();
    }

    public String getAeid() {
        return aeid;
    }

    public void setAeid(String aeid) {
        this.aeid = aeid == null ? null : aeid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getAimdcid() {
        return aimdcid;
    }

    public void setAimdcid(String aimdcid) {
        this.aimdcid = aimdcid == null ? null : aimdcid.trim();
    }

    public String getAimpcid() {
        return aimpcid;
    }

    public void setAimpcid(String aimpcid) {
        this.aimpcid = aimpcid == null ? null : aimpcid.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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
        return "JobTransferApplication{" +
                "jtaid='" + jtaid + '\'' +
                ", aeid='" + aeid + '\'' +
                ", eid='" + eid + '\'' +
                ", aimdcid='" + aimdcid + '\'' +
                ", aimpcid='" + aimpcid + '\'' +
                ", reason='" + reason + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
 //创建带参Parcel构造器
     protected JobTransferApplication(Parcel in) {
         //这里read字段的顺序要与write的顺序一致

         jtaid = in.readString();
         aeid = in.readString();
         eid = in.readString();
         aimdcid = in.readString();
         aimpcid = in.readString();
         reason = in.readString();
         orderby = in.readInt();
         createtime = in.readLong();
          updatetime = in.readLong();
     }

     //创建常量Creator，并实现该接口的两个方法
     public static final Creator<JobTransferApplication> CREATOR = new Creator<JobTransferApplication>() {
         @Override
         public JobTransferApplication createFromParcel(Parcel in) {
             return new JobTransferApplication(in);
         }

         @Override
         public JobTransferApplication[] newArray(int size) {
             return new JobTransferApplication[size];
         }
     };

     @Override
     public int describeContents() {
         return 0;
     }

     @Override
     public void writeToParcel(Parcel parcel, int i) {
         parcel.writeString(jtaid);
         parcel.writeString(aeid);
         parcel.writeString(eid);
          parcel.writeString(aimdcid);
         parcel.writeString(aimpcid);
          parcel.writeString(reason);
         parcel.writeInt(orderby);
         parcel.writeLong(createtime);
         parcel.writeLong(updatetime);
     }

     public static Creator<JobTransferApplication> getCREATOR() {
         return CREATOR;
     }
}