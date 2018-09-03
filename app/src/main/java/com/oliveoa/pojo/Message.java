package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class Message implements Parcelable{
    private String mid;
    private String seid;
    private String eid;
    private String msg;
    private int orderby;
    private long createtime;
    private long updatetime;

    public Message() {
    }

    public Message(String mid, String seid, String eid, String msg, int orderby, long createtime, long updatetime) {
        this.mid = mid;
        this.seid = seid;
        this.eid = eid;
        this.msg = msg;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
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
        return "Message{" +
                "mid='" + mid + '\'' +
                ", seid='" + seid + '\'' +
                ", eid='" + eid + '\'' +
                ", msg='" + msg + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected Message(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             mid = in.readString();
             seid = in.readString();
             eid = in.readString();
             msg = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
             updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<Message> CREATOR = new Creator<Message>() {
             @Override
             public Message createFromParcel(Parcel in) {
                 return new Message(in);
             }

             @Override
             public Message[] newArray(int size) {
                 return new Message[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(mid);
             parcel.writeString(seid);
             parcel.writeString(eid);
              parcel.writeString(msg);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<Message> getCREATOR() {
             return CREATOR;
         }
}
