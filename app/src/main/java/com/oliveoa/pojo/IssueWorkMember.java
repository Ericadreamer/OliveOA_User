package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;

@Entity
@Keep
public class IssueWorkMember implements Parcelable{
    private String iwmid;

    private String iwid;

    private String eid;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public IssueWorkMember(String iwmid, String iwid, String eid, Integer orderby, long createtime, long updatetime) {
        this.iwmid = iwmid;
        this.iwid = iwid;
        this.eid = eid;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public IssueWorkMember() {
        super();
    }

    public String getIwmid() {
        return iwmid;
    }

    public void setIwmid(String iwmid) {
        this.iwmid = iwmid == null ? null : iwmid.trim();
    }

    public String getIwid() {
        return iwid;
    }

    public void setIwid(String iwid) {
        this.iwid = iwid == null ? null : iwid.trim();
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
        return "IssueWorkMember{" +
                "iwmid='" + iwmid + '\'' +
                ", iwid='" + iwid + '\'' +
                ", eid='" + eid + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
     //创建带参Parcel构造器
         protected IssueWorkMember(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             iwmid = in.readString();
             iwid = in.readString();
             eid = in.readString();
             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<IssueWorkMember> CREATOR = new Creator<IssueWorkMember>() {
             @Override
             public IssueWorkMember createFromParcel(Parcel in) {
                 return new IssueWorkMember(in);
             }

             @Override
             public IssueWorkMember[] newArray(int size) {
                 return new IssueWorkMember[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(iwmid);
             parcel.writeString(iwid);
             parcel.writeString(eid);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<IssueWorkMember> getCREATOR() {
             return CREATOR;
         }
}