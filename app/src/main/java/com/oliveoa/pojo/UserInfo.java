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

    public UserInfo() {
    }

    public UserInfo(String id,String name,String sex,String birth,String tel,String email,String address) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.birth = birth;
        this.tel = tel;
        this.email = email;
        this.address =address;
    }

    public String getId() {
        return name;
    }

    public void setId(String id) {
        this.name = name;
    }

    public String getName() {
        return password;
    }

    public void setName(String name) {
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
}
