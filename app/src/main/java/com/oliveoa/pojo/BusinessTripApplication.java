package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class BusinessTripApplication implements Parcelable{
    private String btaid;
    private String eid;
    private long begintime;
    private long endtime;
    private String place;
    private String task;
    private int orderby;
    private long createime;
    private long updatetime;

    public BusinessTripApplication() {
    }

    public BusinessTripApplication(String btaid, String eid, long begintime, long endtime, String place, String task, int orderby, long createime, long updatetime) {
        this.btaid = btaid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.place = place;
        this.task = task;
        this.orderby = orderby;
        this.createime = createime;
        this.updatetime = updatetime;
    }

    public String getBtaid() {
        return btaid;
    }

    public void setBtaid(String btaid) {
        this.btaid = btaid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public long getCreateime() {
        return createime;
    }

    public void setCreateime(long createime) {
        this.createime = createime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "BusinessTripApplication{" +
                "btaid='" + btaid + '\'' +
                ", eid='" + eid + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", place='" + place + '\'' +
                ", task='" + task + '\'' +
                ", orderby=" + orderby +
                ", createime=" + createime +
                ", updatetime=" + updatetime +
                '}';
    }

      //创建带参Parcel构造器
          protected BusinessTripApplication(Parcel in) {
              //这里read字段的顺序要与write的顺序一致

              btaid = in.readString();
              eid = in.readString();
              begintime = in.readLong();
              endtime = in.readLong();
              place = in.readString();
              task= in.readString();
              orderby = in.readInt();
              createime = in.readLong();
              updatetime = in.readLong();
          }

          //创建常量Creator，并实现该接口的两个方法
          public static final Creator<BusinessTripApplication> CREATOR = new Creator<BusinessTripApplication>() {
              @Override
              public BusinessTripApplication createFromParcel(Parcel in) {
                  return new BusinessTripApplication(in);
              }

              @Override
              public BusinessTripApplication[] newArray(int size) {
                  return new BusinessTripApplication[size];
              }
          };

          @Override
          public int describeContents() {
              return 0;
          }

          @Override
          public void writeToParcel(Parcel parcel, int i) {
              parcel.writeString(btaid);
              parcel.writeString(eid);
              parcel.writeLong(begintime);
              parcel.writeLong(endtime);
              parcel.writeString(place);
              parcel.writeString(task);

              parcel.writeInt(orderby);
              parcel.writeLong(createime);
              parcel.writeLong(updatetime);
          }

          public static Creator<BusinessTripApplication> getCREATOR() {
              return CREATOR;
          }
}

