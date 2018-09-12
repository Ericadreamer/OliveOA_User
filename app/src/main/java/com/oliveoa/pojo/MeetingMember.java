package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;

@Keep
@Entity
public class MeetingMember implements Parcelable{
    private String maid;

    private String eid;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public MeetingMember(String maid, String eid, Integer orderby, long createtime, long updatetime) {
        this.maid = maid;
        this.eid = eid;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public MeetingMember() {
        super();
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid == null ? null : maid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
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
        return "MeetingMember{" +
                "maid='" + maid + '\'' +
                ", eid='" + eid + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
     //创建带参Parcel构造器
         protected MeetingMember(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             maid = in.readString();
             eid = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<MeetingMember> CREATOR = new Creator<MeetingMember>() {
             @Override
             public MeetingMember createFromParcel(Parcel in) {
                 return new MeetingMember(in);
             }

             @Override
             public MeetingMember[] newArray(int size) {
                 return new MeetingMember[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(maid);
             parcel.writeString(eid);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<MeetingMember> getCREATOR() {
             return CREATOR;
         }
}