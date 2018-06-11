package com.oliveoa.jsonbean;

import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.UserInfo;

import java.util.ArrayList;

public class IssueWorkJsonBean {
    private ArrayList<IssueWork> issueWorks;
    private ArrayList<UserInfo> employeeslist;

    public IssueWorkJsonBean() {
    }

    public IssueWorkJsonBean(ArrayList<IssueWork> issueWorks, ArrayList<UserInfo> employeeslist) {
        this.issueWorks = issueWorks;
        this.employeeslist = employeeslist;
    }

    public ArrayList<IssueWork> getIssueWorks() {
        return issueWorks;
    }

    public void setIssueWorks(ArrayList<IssueWork> issueWorks) {
        this.issueWorks = issueWorks;
    }

    public ArrayList<UserInfo> getEmployeeslist() {
        return employeeslist;
    }

    public void setEmployeeslist(ArrayList<UserInfo> employeeslist) {
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
