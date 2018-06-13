package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.ContactInfo;

public class EmpContactListJsonBean {
    private ContactInfo employee;
    private DutyInfo position;

    public EmpContactListJsonBean() {
    }

    public EmpContactListJsonBean(ContactInfo employee, DutyInfo position) {
        this.employee = employee;
        this.position = position;
    }

    public ContactInfo getEmployee() {
        return employee;
    }

    public void setEmployee(ContactInfo employee) {
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
