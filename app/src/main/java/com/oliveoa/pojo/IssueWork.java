package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class IssueWork implements Parcelable{
    private String iwid;
    private String eid;
    private String content;
    private long begintime;
    private long endtime;
    private int orderby;
    private long createtime;
    private long updatetime;

    public IssueWork() {
    }

    public IssueWork(String iwid, String eid, String content, long begintime, long endtime, int orderby, long createtime, long updatetime) {
        this.iwid = iwid;
        this.eid = eid;
        this.content = content;
        this.begintime = begintime;
        this.endtime = endtime;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getIwid() {
        return iwid;
    }

    public void setIwid(String iwid) {
        this.iwid = iwid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "IssueWork{" +
                "iwid='" + iwid + '\'' +
                ", eid='" + eid + '\'' +
                ", content='" + content + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
     //创建带参Parcel构造器
         protected IssueWork(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             iwid = in.readString();
             eid = in.readString();
             content = in.readString();
             begintime = in.readLong();
             endtime  = in.readLong();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<IssueWork> CREATOR = new Creator<IssueWork>() {
             @Override
             public IssueWork createFromParcel(Parcel in) {
                 return new IssueWork(in);
             }

             @Override
             public IssueWork[] newArray(int size) {
                 return new IssueWork[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(iwid);
             parcel.writeString(eid);
             parcel.writeString(content);
             parcel.writeLong(begintime);
             parcel.writeLong(endtime);


             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<IssueWork> getCREATOR() {
             return CREATOR;
         }
}
