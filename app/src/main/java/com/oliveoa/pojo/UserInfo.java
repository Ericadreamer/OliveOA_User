package com.oliveoa.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

/* JSON 数据抽象为实体类 */
@Entity
@Keep
public class UserInfo implements Parcelable{
    private String name;
    private String sex;
    private String birth;
    private String tel;
    private String email;
    private String address;
    private String eid;
    private String dcid;
    private String pcid;
    private String id;
    private int orderby ;
    private long createtime;
    private long updatetime;

    public UserInfo() {
    }

    public UserInfo(String name, String sex, String birth, String tel, String email, String address, String eid, String dcid, String pcid, String id, int orderby, long createtime, long updatetime) {
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.eid = eid;
        this.dcid = dcid;
        this.pcid = pcid;
        this.id = id;
        this.orderby = orderby;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDcid() {
        return dcid;
    }

    public void setDcid(String dcid) {
        this.dcid = dcid;
    }

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", eid='" + eid + '\'' +
                ", dcid='" + dcid + '\'' +
                ", pcid='" + pcid + '\'' +
                ", id='" + id + '\'' +
                ", orderby=" + orderby +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }

     //创建带参Parcel构造器
         protected UserInfo(Parcel in) {
             //这里read字段的顺序要与write的顺序一致

             name = in.readString();
             sex = in.readString();
             birth = in.readString();
             tel = in.readString();
             email = in.readString();
             address = in.readString();
             eid = in.readString();
             dcid = in.readString();
             pcid = in.readString();
             id = in.readString();

             orderby = in.readInt();
             createtime = in.readLong();
              updatetime = in.readLong();
         }

         //创建常量Creator，并实现该接口的两个方法
         public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
             @Override
             public UserInfo createFromParcel(Parcel in) {
                 return new UserInfo(in);
             }

             @Override
             public UserInfo[] newArray(int size) {
                 return new UserInfo[size];
             }
         };

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {
             parcel.writeString(name);
             parcel.writeString(sex);
             parcel.writeString(birth);
              parcel.writeString(tel);
             parcel.writeString(email);
              parcel.writeString(address);
             parcel.writeString(eid);
             parcel.writeString(dcid);
             parcel.writeString(pcid);
             parcel.writeString(id);
             parcel.writeInt(orderby);
             parcel.writeLong(createtime);
             parcel.writeLong(updatetime);
         }

         public static Creator<UserInfo> getCREATOR() {
             return CREATOR;
         }
}
