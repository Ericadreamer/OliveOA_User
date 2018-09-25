package com.oliveoa.jsonbean;

import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;

import java.util.ArrayList;

public class LeaveOfficeApplicationJsonBean {
    private LeaveOfficeApplication leaveOfficeApplication;
    private ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinionList;

    public LeaveOfficeApplicationJsonBean() {
    }

    public LeaveOfficeApplicationJsonBean(LeaveOfficeApplication leaveOfficeApplication, ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinionsList) {
        this.leaveOfficeApplication = leaveOfficeApplication;
        this.leaveOfficeApplicationApprovedOpinionList = leaveOfficeApplicationApprovedOpinionList;
    }

    public LeaveOfficeApplication getLeaveOfficeApplication() {
        return leaveOfficeApplication;
    }

    public void setLeaveOfficeApplication(LeaveOfficeApplication leaveOfficeApplication) {
        this.leaveOfficeApplication = leaveOfficeApplication;
    }

    public ArrayList<LeaveOfficeApplicationApprovedOpinion> getLeaveOfficeApplicationApprovedOpinionList() {
        return leaveOfficeApplicationApprovedOpinionList;
    }

    public void setLeaveOfficeApplicationApprovedOpinionList(ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinionList) {
        this.leaveOfficeApplicationApprovedOpinionList = leaveOfficeApplicationApprovedOpinionList;
    }

    @Override
    public String toString() {
        return "LeaveOfficeApplicationJsonBean{" +
                "leaveOfficeApplication=" + leaveOfficeApplication +
                ", leaveOfficeApplicationApprovedOpinionList=" + leaveOfficeApplicationApprovedOpinionList +
                '}';
    }
}
