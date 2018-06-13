package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity@Keep
public class UserLoginInfo {
    private String id ;
    private String password;

    public UserLoginInfo() {
    }

    public UserLoginInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
