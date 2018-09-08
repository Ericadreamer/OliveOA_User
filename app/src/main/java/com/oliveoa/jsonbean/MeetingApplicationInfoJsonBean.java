package com.oliveoa.jsonbean;

import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;

import java.util.ArrayList;

public class MeetingApplicationInfoJsonBean {
    private MeetingApplication meetingApplication;
    private ArrayList<MeetingMember> meetingMembers;

    public MeetingApplicationInfoJsonBean(MeetingApplication meetingApplication, ArrayList<MeetingMember> meetingMembers) {
        this.meetingApplication = meetingApplication;
        this.meetingMembers = meetingMembers;
    }

    public MeetingApplicationInfoJsonBean() {
    }

    public MeetingApplication getMeetingApplication() {
        return meetingApplication;
    }

    public void setMeetingApplication(MeetingApplication meetingApplication) {
        this.meetingApplication = meetingApplication;
    }

    public ArrayList<MeetingMember> getMeetingMembers() {
        return meetingMembers;
    }

    public void setMeetingMembers(ArrayList<MeetingMember> meetingMembers) {
        this.meetingMembers = meetingMembers;
    }

    @Override
    public String toString() {
        return "MeetingApplicationInfoJsonBean{" +
                "meetingApplication=" + meetingApplication +
                ", meetingMembers=" + meetingMembers +
                '}';
    }
}
