package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class MeetingApplication implements Parcelable{
    private String maid;

    private String eid;

    private String aeid;

    private String theme;

    private long begintime;

    private long endtime;

    private String place;

    private Integer isapproved;

    private String opinion;

    private Integer orderby;

    private long createtime;

    private long updatetime;


    public MeetingApplication() {
        super();
    }

    public MeetingApplication(String maid, String eid, String aeid, String theme, long begintime, long endtime, String place, Integer isapproved, String opinion, Integer orderby, long updatetime, long createtime) {
        this.maid = maid;
        this.eid = eid;
        this.aeid = aeid;
        this.theme = theme;
        this.begintime = begintime;
        this.endtime = endtime;
        this.place = place;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.updatetime = updatetime;
        this.createtime = createtime;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getAeid() {
        return aeid;
    }

    public void setAeid(String aeid) {
        this.aeid = aeid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(Integer isapproved) {
        this.isapproved = isapproved;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "MeetingApplication{" +
                "maid='" + maid + '\'' +
                ", eid='" + eid + '\'' +
                ", aeid='" + aeid + '\'' +
                ", theme='" + theme + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", place='" + place + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected MeetingApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             maid = in.readString();
             eid  = in.readString();
             aeid = in.readString();
             theme = in.readString();
             begintime = in.readLong();
             endtime = in.readLong();
             place = in.readString();
             isapproved = in.readInt();
             opinion =in.readString();
             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<MeetingApplication> CREATOR = new Creator<MeetingApplication>() {
             @Override
             public MeetingApplication createFromParcel(Parcel in) {
                 return new MeetingApplication(in);
             }

             @Override
             public MeetingApplication[] newArray(int size) {
                 return new MeetingApplication[size];
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
             parcel.writeString(aeid);
             parcel.writeString(theme);
             parcel.writeLong(begintime);
             parcel.writeLong(endtime);
             parcel.writeString(place);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<MeetingApplication> getCREATOR() {
             return CREATOR;
         }
}