package com.oliveoa.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;

@Entity
@Keep
public class ApproveNumber {  //审批人、会议参加人、签发部门
    public String id;


    public ApproveNumber() {
    }

    public ApproveNumber(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": \""+ id + '\"' +
                '}';
    }
}
