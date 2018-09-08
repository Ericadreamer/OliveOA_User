package com.oliveoa.jsonbean;

import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;

import java.util.ArrayList;

public class LeaveOfficeApplicationJsonBean {
    private LeaveOfficeApplication leaveOfficeApplication;
    private ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinions;

    public LeaveOfficeApplicationJsonBean() {
    }

    public LeaveOfficeApplicationJsonBean(LeaveOfficeApplication leaveOfficeApplication, ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinions) {
        this.leaveOfficeApplication = leaveOfficeApplication;
        this.leaveOfficeApplicationApprovedOpinions = leaveOfficeApplicationApprovedOpinions;
    }

    public LeaveOfficeApplication getLeaveOfficeApplication() {
        return leaveOfficeApplication;
    }

    public void setLeaveOfficeApplication(LeaveOfficeApplication leaveOfficeApplication) {
        this.leaveOfficeApplication = leaveOfficeApplication;
    }

    public ArrayList<LeaveOfficeApplicationApprovedOpinion> getLeaveOfficeApplicationApprovedOpinions() {
        return leaveOfficeApplicationApprovedOpinions;
    }

    public void setLeaveOfficeApplicationApprovedOpinions(ArrayList<LeaveOfficeApplicationApprovedOpinion> leaveOfficeApplicationApprovedOpinions) {
        this.leaveOfficeApplicationApprovedOpinions = leaveOfficeApplicationApprovedOpinions;
    }

    @Override
    public String toString() {
        return "LeaveOfficeApplicationJsonBean{" +
                "leaveOfficeApplication=" + leaveOfficeApplication +
                ", leaveOfficeApplicationApprovedOpinions=" + leaveOfficeApplicationApprovedOpinions +
                '}';
    }
}
