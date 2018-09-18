package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Keep
@Entity
public class NoteInfo implements Parcelable{
    private String content;
    private String updatetime;
    private int orderby;

    public NoteInfo() {
    }

    public NoteInfo(String content, String updatetime) {
        this.content = content;
        this.updatetime = updatetime;
    }

    public NoteInfo(String content, String updatetime, int orderby) {
        this.content = content;
        this.updatetime = updatetime;
        this.orderby = orderby;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    @Override
    public String toString() {
        return "NoteInfo{" +
                "content='" + content + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", orderby=" + orderby +
                '}';
    }

    //创建带参Parcel构造器
         protected NoteInfo(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             content = in.readString();
             updatetime = in.readString();
             orderby = in.readInt();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
             @Override
             public NoteInfo createFromParcel(Parcel in) {
                 return new NoteInfo(in);
             }

             @Override
             public NoteInfo[] newArray(int size) {
                 return new NoteInfo[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(content);
             parcel.writeString(updatetime);
             parcel.writeInt(orderby);
         }

         public static Creator<NoteInfo> getCREATOR() {
             return CREATOR;
         }
}
