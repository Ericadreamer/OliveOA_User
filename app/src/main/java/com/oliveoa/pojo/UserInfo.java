package com.oliveoa.pojo;

/* JSON 数据抽象为实体类 */
public class UserInfo {
    private String name;
    private String password;
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

    public UserInfo(String name, String password, String sex, String birth, String tel, String email, String address, String eid, String dcid, String pcid, String id, int orderby, long createtime, long updatetime) {
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password + '\'' +
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
}
