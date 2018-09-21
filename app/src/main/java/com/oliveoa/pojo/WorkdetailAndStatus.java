package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class WorkdetailAndStatus implements Parcelable{
    private String waid;
    private String theme;
    private String starttime;
    private String endtime;
    private int status;

    public WorkdetailAndStatus() {
    }

    public WorkdetailAndStatus(String waid, String theme, String starttime, String endtime, int status) {
        this.waid = waid;
        this.theme = theme;
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
    }

    public String getWaid() {
        return waid;
    }

    public void setWaid(String waid) {
        this.waid = waid;
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
                "waid='" + waid + '\'' +
                ", theme='" + theme + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", status=" + status +
                '}';
    }
     //创建带参Parcel构造器
         protected WorkdetailAndStatus(Parcel in) {
             //这里read字段的顺序要与write的顺序一致
     
             waid = in.readString();
             theme = in.readString();
             starttime = in.readString();
             endtime = in.readString();

             status = in.readInt();

         }
     
         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<WorkdetailAndStatus> CREATOR = new Creator<WorkdetailAndStatus>() {
             @Override
             public WorkdetailAndStatus createFromParcel(Parcel in) {
                 return new WorkdetailAndStatus(in);
             }
     
             @Override
             public WorkdetailAndStatus[] newArray(int size) {
                 return new WorkdetailAndStatus[size];
             }
         };
     
         @Override
         public int describeContents() {
             return 0;
         }
     
         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(waid);
             parcel.writeString(theme);
             parcel.writeString(starttime);
              parcel.writeString(endtime);
             parcel.writeInt(status);
         }
     
         public static Creator<WorkdetailAndStatus> getCREATOR() {
             return CREATOR;
         }
}
