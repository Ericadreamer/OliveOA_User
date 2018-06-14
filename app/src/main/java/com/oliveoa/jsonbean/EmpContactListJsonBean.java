package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.ContactInfo;

public class EmpContactListJsonBean {
    private ContactInfo employees;
    private DutyInfo position;

    public EmpContactListJsonBean() {
    }

    public EmpContactListJsonBean(ContactInfo employees, DutyInfo position) {
        this.employees = employees;
        this.position = position;
    }

    public ContactInfo getEmployee() {
        return employees;
    }

    public void setEmployee(ContactInfo employees) {
        this.employees = employees;
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
                "employees=" + employees +
                ", position=" + position +
                '}';
    }
}
