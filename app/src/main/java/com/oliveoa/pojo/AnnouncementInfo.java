package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class AnnouncementInfo implements Parcelable {
    private String aid;
    private String eid;
    private String title;
    private String content;
    private String signature;
    private long publishtime;
    private int isapproved;
    private int orderby;
    private long createtime;
    private long updatetime;

    public AnnouncementInfo() {
    }

    public AnnouncementInfo(String aid, String eid, String title, String content, String signature, long publishtime, int isapproved, int orderby, long createtime, long updatetime) {
        this.aid = aid;
        this.eid = eid;
        this.title = title;
        this.content = content;
        this.signature = signature;
        this.publishtime = publishtime;
        this.isapproved = isapproved;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(long publishtime) {
        this.publishtime = publishtime;
    }

    public int getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(int isapproved) {
        this.isapproved = isapproved;
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
        return "AnnouncementInfo{" +
                "aid='" + aid + '\'' +
                ", eid='" + eid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", signature='" + signature + '\'' +
                ", publishtime=" + publishtime +
                ", isapproved=" + isapproved +
                ", orderby=" + orderby +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }

     //创建带参Parcel构造器
         protected AnnouncementInfo(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             aid = in.readString();
             eid = in.readString();
             title = in.readString();
             content = in.readString();
             signature = in.readString();
             publishtime = in.readLong();
             isapproved = in.readInt();
             orderby = in.readInt();
             createtime = in.readLong();
             updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<AnnouncementInfo> CREATOR = new Creator<AnnouncementInfo>() {
             @Override
             public AnnouncementInfo createFromParcel(Parcel in) {
                 return new AnnouncementInfo(in);
             }

             @Override
             public AnnouncementInfo[] newArray(int size) {
                 return new AnnouncementInfo[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(aid);
             parcel.writeString(eid);
             parcel.writeString(title);
             parcel.writeString(content);
             parcel.writeString(signature);
             parcel.writeLong(publishtime);
             parcel.writeInt(isapproved);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<AnnouncementInfo> getCREATOR() {
             return CREATOR;
         }
}
