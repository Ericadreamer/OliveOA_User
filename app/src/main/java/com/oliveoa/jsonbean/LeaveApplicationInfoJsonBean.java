package com.oliveoa.jsonbean;

import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;

import java.util.ArrayList;

public class LeaveApplicationInfoJsonBean {
    private LeaveApplication leaveApplication;
    private ArrayList<LeaveApplicationApprovedOpinionList> leaveApplicationApprovedOpinionList;

    public LeaveApplicationInfoJsonBean() {
    }

    public LeaveApplicationInfoJsonBean(LeaveApplication leaveApplication, ArrayList<LeaveApplicationApprovedOpinionList> leaveApplicationApprovedOpinionLists) {
        this.leaveApplication = leaveApplication;
        this.leaveApplicationApprovedOpinionList= leaveApplicationApprovedOpinionLists;
    }

    public LeaveApplication getLeaveApplication() {
        return leaveApplication;
    }

    public void setLeaveApplication(LeaveApplication leaveApplication) {
        this.leaveApplication = leaveApplication;
    }

    public ArrayList<LeaveApplicationApprovedOpinionList> getLeaveApplicationApprovedOpinionLists() {
        return leaveApplicationApprovedOpinionList;
    }

    public void setLeaveApplicationApprovedOpinionLists(ArrayList<LeaveApplicationApprovedOpinionList> leaveApplicationApprovedOpinionLists) {
        this.leaveApplicationApprovedOpinionList= leaveApplicationApprovedOpinionLists;
    }

    @Override
    public String toString() {
        return "LeaveApplicationInfoJsonBean{" +
                "leaveApplication=" + leaveApplication +
                ", leaveApplicationApprovedOpinionLists=" + leaveApplicationApprovedOpinionList+
                '}';
    }
}
