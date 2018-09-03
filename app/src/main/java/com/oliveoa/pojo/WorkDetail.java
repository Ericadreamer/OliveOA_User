package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class WorkDetail implements Parcelable{
    private String swid;
    private String seid;
    private String aeid;
    private String content;
    private long begintime;
    private long endtime;
    private int isapproved;
    private String opinion;
    private int orderby;
    private long createtime;
    private long updatetime;

    public WorkDetail() {
    }

    public WorkDetail(String swid, String seid, String aeid, String content, long begintime, long endtime, int isapproved, String opinion, int orderby, long createtime, long updatetime) {
        this.swid = swid;
        this.seid = seid;
        this.aeid = aeid;
        this.content = content;
        this.begintime = begintime;
        this.endtime = endtime;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getSwid() {
        return swid;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getAeid() {
        return aeid;
    }

    public void setAeid(String aeid) {
        this.aeid = aeid;
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

    public int getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(int isapproved) {
        this.isapproved = isapproved;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
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
        return "WorkDetail{" +
                "swid='" + swid + '\'' +
                ", seid='" + seid + '\'' +
                ", aeid='" + aeid + '\'' +
                ", content='" + content + '\'' +
                ", begintime=" + begintime +
                ", endtime=" + endtime +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected WorkDetail(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

            swid  = in.readString();
            seid  = in.readString();
            aeid  = in.readString();
            content  = in.readString();
            begintime  = in.readLong();
            endtime = in.readLong();
            isapproved = in.readInt();
            opinion  = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<WorkDetail> CREATOR = new Creator<WorkDetail>() {
             @Override
             public WorkDetail createFromParcel(Parcel in) {
                 return new WorkDetail(in);
             }

             @Override
             public WorkDetail[] newArray(int size) {
                 return new WorkDetail[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(swid);
             parcel.writeString(seid);
             parcel.writeString(aeid);
             parcel.writeString(content);
             parcel.writeLong(begintime);
             parcel.writeLong(endtime);
             parcel.writeInt(isapproved);
             parcel.writeString(opinion);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<WorkDetail> getCREATOR() {
             return CREATOR;
         }
}
