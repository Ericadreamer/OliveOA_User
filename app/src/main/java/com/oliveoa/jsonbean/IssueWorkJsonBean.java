package com.oliveoa.jsonbean;

import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.ContactInfo;

import java.util.ArrayList;

public class IssueWorkJsonBean {
    private ArrayList<IssueWork> issueWorks;
    private ArrayList<ContactInfo> employeeslist;

    public IssueWorkJsonBean() {
    }

    public IssueWorkJsonBean(ArrayList<IssueWork> issueWorks, ArrayList<ContactInfo> employeeslist) {
        this.issueWorks = issueWorks;
        this.employeeslist = employeeslist;
    }

    public ArrayList<IssueWork> getIssueWorks() {
        return issueWorks;
    }

    public void setIssueWorks(ArrayList<IssueWork> issueWorks) {
        this.issueWorks = issueWorks;
    }

    public ArrayList<ContactInfo> getEmployeeslist() {
        return employeeslist;
    }

    public void setEmployeeslist(ArrayList<ContactInfo> employeeslist) {
        this.employeeslist = employeeslist;
    }

    @Override
    public String toString() {
        return "IssueWorkJsonBean{" +
                "issueWorks=" + issueWorks +
                ", employeeslist=" + employeeslist +
                '}';
    }
}
