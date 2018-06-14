package com.oliveoa.jsonbean;

import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;

import java.util.ArrayList;

public class OvertimeApplicationJsonBean {
    private OvertimeApplication overtimeApplication;
    private ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionList;

    public OvertimeApplicationJsonBean() {
    }

    public OvertimeApplicationJsonBean(OvertimeApplication overtimeApplication, ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionList) {
        this.overtimeApplication = overtimeApplication;
        this.overtimeApplicationApprovedOpinionList = overtimeApplicationApprovedOpinionList;
    }

    public OvertimeApplication getOvertimeApplications() {
        return overtimeApplication;
    }

    public void setOvertimeApplications(OvertimeApplication overtimeApplication) {
        this.overtimeApplication = overtimeApplication;
    }

    public ArrayList<OvertimeApplicationApprovedOpinionList> getOvertimeApplicationApprovedOpinionLists() {
        return overtimeApplicationApprovedOpinionList;
    }

    public void setOvertimeApplicationApprovedOpinionLists(ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionList) {
        this.overtimeApplicationApprovedOpinionList = overtimeApplicationApprovedOpinionList;
    }

    @Override
    public String toString() {
        return "OvertimeApplicationJsonBean{" +
                "overtimeApplication=" + overtimeApplication +
                ", overtimeApplicationApprovedOpinionList=" + overtimeApplicationApprovedOpinionList +
                '}';
    }
}
