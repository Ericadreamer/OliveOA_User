package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class MeetingApplicationAndStatus implements Parcelable{
    private String maid;
    private String theme;
    private String starttime;
    private String endtime;
    private int status;

    public MeetingApplicationAndStatus() {
    }

    public MeetingApplicationAndStatus(String maid, String theme, String starttime, String endtime, int status) {
        this.maid = maid;
        this.theme = theme;
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MeetingApplicationAndStatus{" +
                "maid='" + maid + '\'' +
                ", theme='" + theme + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", status=" + status +
                '}';
    }
     //创建带参Parcel构造器
         protected MeetingApplicationAndStatus(Parcel in) {
             //这里read字段的顺序要与write的顺序一致
     
             maid = in.readString();
             theme = in.readString();
             starttime = in.readString();
             endtime = in.readString();

             status = in.readInt();

         }
     
         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<MeetingApplicationAndStatus> CREATOR = new Creator<MeetingApplicationAndStatus>() {
             @Override
             public MeetingApplicationAndStatus createFromParcel(Parcel in) {
                 return new MeetingApplicationAndStatus(in);
             }
     
             @Override
             public MeetingApplicationAndStatus[] newArray(int size) {
                 return new MeetingApplicationAndStatus[size];
             }
         };
     
         @Override
         public int describeContents() {
             return 0;
         }
     
         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(maid);
             parcel.writeString(theme);
             parcel.writeString(starttime);
              parcel.writeString(endtime);
             parcel.writeInt(status);
         }
     
         public static Creator<MeetingApplicationAndStatus> getCREATOR() {
             return CREATOR;
         }
}
