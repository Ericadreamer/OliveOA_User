package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;


@Keep
@Entity
public class Approval implements Parcelable{
    private String aid;
    private String seid;
    private String content;
    private int status; //0未审批，1已审批
    private int type;//1-8 申请，9公告
    private int isapprove; // -1不同意 1 同意 0正在审核 -2未审核

    public Approval() {
    }

    public Approval(String aid, String seid, String content, int status, int type, int isapprove) {
        this.aid = aid;
        this.seid = seid;
        this.content = content;
        this.status = status;
        this.type = type;
        this.isapprove = isapprove;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(int isapprove) {
        this.isapprove = isapprove;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "aid='" + aid + '\'' +
                ", seid='" + seid + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", isapprove=" + isapprove +
                '}';
    }

    //创建带参Parcel构造器
         protected Approval(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             aid = in.readString();
             seid = in.readString();
             content = in.readString();
             status = in.readInt();
             type = in.readInt();
             isapprove = in.readInt();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<Approval> CREATOR = new Creator<Approval>() {
             @Override
             public Approval createFromParcel(Parcel in) {
                 return new Approval(in);
             }

             @Override
             public Approval[] newArray(int size) {
                 return new Approval[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(aid);
             parcel.writeString(seid);
             parcel.writeString(content);
             parcel.writeInt(status);
             parcel.writeInt(type);
             parcel.writeInt(isapprove);
         }

         public static Creator<Approval> getCREATOR() {
             return CREATOR;
         }

}
