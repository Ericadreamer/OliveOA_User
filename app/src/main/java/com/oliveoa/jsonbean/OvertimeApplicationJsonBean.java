package com.oliveoa.jsonbean;

import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;

import java.util.ArrayList;

public class OvertimeApplicationJsonBean {
    private ArrayList<OvertimeApplication> overtimeApplications;
    private ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionLists;

    public OvertimeApplicationJsonBean() {
    }

    public OvertimeApplicationJsonBean(ArrayList<OvertimeApplication> overtimeApplications, ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionLists) {
        this.overtimeApplications = overtimeApplications;
        this.overtimeApplicationApprovedOpinionLists = overtimeApplicationApprovedOpinionLists;
    }

    public ArrayList<OvertimeApplication> getOvertimeApplications() {
        return overtimeApplications;
    }

    public void setOvertimeApplications(ArrayList<OvertimeApplication> overtimeApplications) {
        this.overtimeApplications = overtimeApplications;
    }

    public ArrayList<OvertimeApplicationApprovedOpinionList> getOvertimeApplicationApprovedOpinionLists() {
        return overtimeApplicationApprovedOpinionLists;
    }

    public void setOvertimeApplicationApprovedOpinionLists(ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionLists) {
        this.overtimeApplicationApprovedOpinionLists = overtimeApplicationApprovedOpinionLists;
    }

    @Override
    public String toString() {
        return "OvertimeApplicationJsonBean{" +
                "overtimeApplications=" + overtimeApplications +
                ", overtimeApplicationApprovedOpinionLists=" + overtimeApplicationApprovedOpinionLists +
                '}';
    }
}
