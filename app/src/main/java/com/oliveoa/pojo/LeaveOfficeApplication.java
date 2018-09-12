package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;

@Keep
@Entity
public class LeaveOfficeApplication implements Parcelable {
    private String loaid;

    private String eid;

    private long leavetime;

    private String reason;

    private String handoverMatters;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public LeaveOfficeApplication(String loaid, String eid, long leavetime, String reason, String handoverMatters, Integer orderby, long createtime, long updatetime) {
        this.loaid = loaid;
        this.eid = eid;
        this.leavetime = leavetime;
        this.reason = reason;
        this.handoverMatters = handoverMatters;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public LeaveOfficeApplication() {
        super();
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

    public long getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(long leavetime) {
        this.leavetime = leavetime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getHandoverMatters() {
        return handoverMatters;
    }

    public void setHandoverMatters(String handoverMatters) {
        this.handoverMatters = handoverMatters == null ? null : handoverMatters.trim();
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
        return "LeaveOfficeApplication{" +
                "loaid='" + loaid + '\'' +
                ", eid='" + eid + '\'' +
                ", leavetime=" + leavetime +
                ", reason='" + reason + '\'' +
                ", handoverMatters='" + handoverMatters + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

    //创建带参Parcel构造器
         protected LeaveOfficeApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

              loaid = in.readString();
              eid= in.readString();
              leavetime= in.readLong();
              reason= in.readString();
              handoverMatters= in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<LeaveOfficeApplication> CREATOR = new Creator<LeaveOfficeApplication>() {
             @Override
             public LeaveOfficeApplication createFromParcel(Parcel in) {
                 return new LeaveOfficeApplication(in);
             }

             @Override
             public LeaveOfficeApplication[] newArray(int size) {
                 return new LeaveOfficeApplication[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(loaid);
             parcel.writeString(eid);
             parcel.writeLong(leavetime);
              parcel.writeString(reason);
             parcel.writeString(handoverMatters);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<LeaveOfficeApplication> getCREATOR() {
             return CREATOR;
         }
}