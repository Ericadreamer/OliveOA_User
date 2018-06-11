package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.UserInfo;

public class EmpContactListJsonBean {
    private UserInfo employee;
    private DutyInfo position;

    public EmpContactListJsonBean() {
    }

    public EmpContactListJsonBean(UserInfo employee, DutyInfo position) {
        this.employee = employee;
        this.position = position;
    }

    public UserInfo getEmployee() {
        return employee;
    }

    public void setEmployee(UserInfo employee) {
        this.employee = employee;
    }

    public DutyInfo getPosition() {
        return position;
    }

    public void setPosition(DutyInfo position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmpContactListJsonBean{" +
                "employee=" + employee +
                ", position=" + position +
                '}';
    }
}
