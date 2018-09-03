package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

@Entity@Keep
public class LeaveApplication implements Parcelable{

    private String laid;
    private String eid;
    private long begintime;
    private long endtime;
    private String reason;
    private int type;
    private long normalRest;
    private long swapRest;
    private long shouldRest;
    private long supplementRest;
    private int orderby;
    @Transient
    private long createtime;
    @Transient
    private long updatetime;

    public LeaveApplication() {
    }

    public LeaveApplication(String laid, String eid, long begintime, long endtime, String reason, int type, long normalRest, long swapRest, long shouldRest, long supplementRest, int orderby) {
        this.laid = laid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.reason = reason;
        this.type = type;
        this.normalRest = normalRest;
        this.swapRest = swapRest;
        this.shouldRest = shouldRest;
        this.supplementRest = supplementRest;
        this.orderby = orderby;
    }

    public LeaveApplication(String laid, String eid, long begintime, long endtime, String reason, int type, long normalRest, long swapRest, long shouldRest, long supplementRest, int orderby, long createtime, long updatetime) {
        this.laid = laid;
        this.eid = eid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.reason = reason;
        this.type = type;
        this.normalRest = normalRest;
        this.swapRest = swapRest;
        this.shouldRest = shouldRest;
        this.supplementRest = supplementRest;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getLaid() {
        return laid;
    }

    public void setLaid(String laid) {
        this.laid = laid;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getNormalRest() {
        return normalRest;
    }

    public void setNormalRest(long normalRest) {
        this.normalRest = normalRest;
    }

    public long getSwapRest() {
        return swapRest;
    }

    public void setSwapRest(long swapRest) {
        this.swapRest = swapRest;
    }

    public long getShouldRest() {
        return shouldRest;
    }

    public void setShouldRest(long shouldRest) {
        this.shouldRest = shouldRest;
    }

    public long getSupplementRest() {
        return supplementRest;
    }

    public void setSupplementRest(long supplementRest) {
        this.supplementRest = supplementRest;
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
        return "LeaveApplication{" +
                "laid='" + laid + '\'' +
                ", eid='" + eid + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", reason='" + reason + '\'' +
                ", type=" + type +
                ", normalRest=" + normalRest +
                ", swapRest=" + swapRest +
                ", shouldRest=" + shouldRest +
                ", supplementRest=" + supplementRest +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected LeaveApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             laid = in.readString();
             eid = in.readString();
             begintime = in.readLong();
             endtime = in.readLong();
             reason = in.readString();
             type = in.readInt();
             normalRest = in.readLong();
             swapRest = in.readLong();
             shouldRest = in.readLong();
             supplementRest = in.readLong();
             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<LeaveApplication> CREATOR = new Creator<LeaveApplication>() {
             @Override
             public LeaveApplication createFromParcel(Parcel in) {
                 return new LeaveApplication(in);
             }

             @Override
             public LeaveApplication[] newArray(int size) {
                 return new LeaveApplication[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(laid);
             parcel.writeString(eid);
             parcel.writeLong(begintime);
             parcel.writeLong(endtime);
             parcel.writeString(reason);
             parcel.writeInt(type);
             parcel.writeLong(normalRest);
             parcel.writeLong(swapRest);
             parcel.writeLong(shouldRest);
             parcel.writeLong(supplementRest);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<LeaveApplication> getCREATOR() {
             return CREATOR;
         }
}
