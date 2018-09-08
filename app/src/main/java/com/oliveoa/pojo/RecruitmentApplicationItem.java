package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RecruitmentApplicationItem implements Parcelable{
    private String raiid;

    private String raid;

    private String pcid;

    private Integer number;

    private String positionDescribe;

    private String positionRequest;

    private String salary;

    private Integer orderby;

    private long createtime;

    private long updatetime;

    public RecruitmentApplicationItem(String raiid, String raid, String pcid, Integer number, String positionDescribe, String positionRequest, String salary, Integer orderby, long createtime, long updatetime) {
        this.raiid = raiid;
        this.raid = raid;
        this.pcid = pcid;
        this.number = number;
        this.positionDescribe = positionDescribe;
        this.positionRequest = positionRequest;
        this.salary = salary;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public RecruitmentApplicationItem() {
        super();
    }

    public String getRaiid() {
        return raiid;
    }

    public void setRaiid(String raiid) {
        this.raiid = raiid == null ? null : raiid.trim();
    }

    public String getRaid() {
        return raid;
    }

    public void setRaid(String raid) {
        this.raid = raid == null ? null : raid.trim();
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid == null ? null : pcid.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPositionDescribe() {
        return positionDescribe;
    }

    public void setPositionDescribe(String positionDescribe) {
        this.positionDescribe = positionDescribe == null ? null : positionDescribe.trim();
    }

    public String getPositionRequest() {
        return positionRequest;
    }

    public void setPositionRequest(String positionRequest) {
        this.positionRequest = positionRequest == null ? null : positionRequest.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
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
        return "RecruitmentApplicationItem{" +
                "raiid='" + raiid + '\'' +
                ", raid='" + raid + '\'' +
                ", pcid='" + pcid + '\'' +
                ", number=" + number +
                ", positionDescribe='" + positionDescribe + '\'' +
                ", positionRequest='" + positionRequest + '\'' +
                ", salary='" + salary + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected RecruitmentApplicationItem(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             raiid = in.readString();
             raid = in.readString();
             pcid = in.readString();
             number = in.readInt();
             positionDescribe = in.readString();
             positionRequest = in.readString();
            salary  = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<RecruitmentApplicationItem> CREATOR = new Creator<RecruitmentApplicationItem>() {
             @Override
             public RecruitmentApplicationItem createFromParcel(Parcel in) {
                 return new RecruitmentApplicationItem(in);
             }

             @Override
             public RecruitmentApplicationItem[] newArray(int size) {
                 return new RecruitmentApplicationItem[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(raiid);
             parcel.writeString(raid);
             parcel.writeString(pcid);
              parcel.writeInt(number);
             parcel.writeString(positionDescribe);
              parcel.writeString(positionRequest);
             parcel.writeString(salary);

             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<RecruitmentApplicationItem> getCREATOR() {
             return CREATOR;
         }
}