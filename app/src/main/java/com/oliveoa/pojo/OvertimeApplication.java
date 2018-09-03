package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class OvertimeApplication implements Parcelable{
    private String oaid;
    private String eid;
    private String reason;
    private long begintime;
    private long endtime;
    private int orderby;
    private long createtime;
    private long updatetime;

    public OvertimeApplication() {
    }

    public OvertimeApplication(String oaid, String eid, String reason, long begintime, long endtime, int orderby, long createtime, long updatetime) {
        this.oaid = oaid;
        this.eid = eid;
        this.reason = reason;
        this.begintime = begintime;
        this.endtime = endtime;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        return "OvertimeApplication{" +
                "oaid='" + oaid + '\'' +
                ", eid='" + eid + '\'' +
                ", reason='" + reason + '\'' +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }


     //创建带参Parcel构造器
         protected OvertimeApplication(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

            oaid  = in.readString();
            eid  = in.readString();
            reason  = in.readString();
            begintime =in.readLong();
            endtime =in.readLong();
             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<OvertimeApplication> CREATOR = new Creator<OvertimeApplication>() {
             @Override
             public OvertimeApplication createFromParcel(Parcel in) {
                 return new OvertimeApplication(in);
             }

             @Override
             public OvertimeApplication[] newArray(int size) {
                 return new OvertimeApplication[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(oaid);
             parcel.writeString(eid);
             parcel.writeString(reason);
             parcel.writeLong(begintime);
             parcel.writeLong(endtime);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<OvertimeApplication> getCREATOR() {
             return CREATOR;
         }
}
