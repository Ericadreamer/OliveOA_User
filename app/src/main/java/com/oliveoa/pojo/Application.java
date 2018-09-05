package com.oliveoa.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class Application implements Parcelable{
    private String describe;
    private String aid;
    private int type;
    private int status;

    public Application() {
    }

    public Application(String describe, String aid, int type, int status) {
        this.describe = describe;
        this.aid = aid;
        this.type = type;
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "describe='" + describe + '\'' +
                ", aid='" + aid + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }

    //创建带参Parcel构造器
         protected Application(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             describe = in.readString();
             aid = in.readString();
             type =in.readInt();
             status = in.readInt();

         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<Application> CREATOR = new Creator<Application>() {
             @Override
             public Application createFromParcel(Parcel in) {
                 return new Application(in);
             }

             @Override
             public Application[] newArray(int size) {
                 return new Application[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(describe);
             parcel.writeString(aid);
             parcel.writeInt(type);
             parcel.writeInt(status);
         }

         public static Creator<Application> getCREATOR() {
             return CREATOR;
         }
}
