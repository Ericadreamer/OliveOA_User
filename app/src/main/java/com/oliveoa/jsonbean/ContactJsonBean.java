package com.oliveoa.jsonbean;

import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;

public class ContactJsonBean {
    private DepartmentInfo department;
    private ArrayList<EmpContactListJsonBean> empContactList;

    public ContactJsonBean() {
    }

    public ContactJsonBean(DepartmentInfo department, ArrayList<EmpContactListJsonBean> empContactList) {
        this.department = department;
        this.empContactList = empContactList;
    }

    public DepartmentInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentInfo department) {
        this.department = department;
    }

    public ArrayList<EmpContactListJsonBean> getEmpContactList() {
        return empContactList;
    }

    public void setEmpContactList(ArrayList<EmpContactListJsonBean> empContactList) {
        this.empContactList = empContactList;
    }

    @Override
    public String toString() {
        return "ContactJsonBean{" +
                "department=" + department +
                ", empContactList=" + empContactList +
                '}';
    }
}
