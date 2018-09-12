package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

import java.util.Date;

@Keep
@Entity
public class RecruitmentApplicationApprovedOpinion implements Parcelable{
    private String raaocid;

    private String raaopid;

    private String raid;

    private String eid;

    private Integer isapproved;

    private String opinion;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public RecruitmentApplicationApprovedOpinion(String raaocid, String raaopid, String raid, String eid, Integer isapproved, String opinion, Integer orderby, long createtime, long updatetime) {
        this.raaocid = raaocid;
        this.raaopid = raaopid;
        this.raid = raid;
        this.eid = eid;
        this.isapproved = isapproved;
        this.opinion = opinion;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public RecruitmentApplicationApprovedOpinion() {
        super();
    }

    public String getRaaocid() {
        return raaocid;
    }

    public void setRaaocid(String raaocid) {
        this.raaocid = raaocid == null ? null : raaocid.trim();
    }

    public String getRaaopid() {
        return raaopid;
    }

    public void setRaaopid(String raaopid) {
        this.raaopid = raaopid == null ? null : raaopid.trim();
    }

    public String getRaid() {
        return raid;
    }

    public void setRaid(String raid) {
        this.raid = raid == null ? null : raid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public Integer getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(Integer isapproved) {
        this.isapproved = isapproved;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
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
        return "RecruitmentApplicationApprovedOpinion{" +
                "raaocid='" + raaocid + '\'' +
                ", raaopid='" + raaopid + '\'' +
                ", raid='" + raid + '\'' +
                ", eid='" + eid + '\'' +
                ", isapproved=" + isapproved +
                ", opinion='" + opinion + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
      //创建带参Parcel构造器
          protected RecruitmentApplicationApprovedOpinion(Parcel in) {
              //这里read字段的顺序要与write的顺序一致

              raaocid = in.readString();
              raaopid = in.readString();
              raid = in.readString();
              eid = in.readString();
              isapproved = in.readInt();
              opinion = in.readString();

              orderby = in.readInt();
              createtime = in.readLong();
              updatetime = in.readLong();
          }

          //创建常量Creator，并实现该接口的两个方法
          public static final Creator<RecruitmentApplicationApprovedOpinion> CREATOR = new Creator<RecruitmentApplicationApprovedOpinion>() {
              @Override
              public RecruitmentApplicationApprovedOpinion createFromParcel(Parcel in) {
                  return new RecruitmentApplicationApprovedOpinion(in);
              }

              @Override
              public RecruitmentApplicationApprovedOpinion[] newArray(int size) {
                  return new RecruitmentApplicationApprovedOpinion[size];
              }
          };

          @Override
          public int describeContents() {
              return 0;
          }

          @Override
          public void writeToParcel(Parcel parcel, int i) {
              parcel.writeString(raaocid);
              parcel.writeString(raaopid);
              parcel.writeString(raid);
               parcel.writeString(eid);
              parcel.writeInt(isapproved);
               parcel.writeString(opinion);

              parcel.writeInt(orderby);
              parcel.writeLong(createtime);
              parcel.writeLong(updatetime);
          }

          public static Creator<RecruitmentApplicationApprovedOpinion> getCREATOR() {
              return CREATOR;
          }
}