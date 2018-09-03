package com.oliveoa.jsonbean;

import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;

import java.util.ArrayList;

public class AnnouncementDataJsonBean {
    private AnnouncementInfo announcement;
    private ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionList;

    public AnnouncementDataJsonBean() {
    }

    public AnnouncementDataJsonBean(AnnouncementInfo announcement, ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionList) {
        this.announcement = announcement;
        this.announcementApprovedOpinionList = announcementApprovedOpinionList;
    }

    public AnnouncementInfo getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementInfo announcement) {
        this.announcement = announcement;
    }

    public ArrayList<AnnouncementApprovedOpinionList> getAnnouncementApprovedOpinionList() {
        return announcementApprovedOpinionList;
    }

    public void setAnnouncementApprovedOpinionList(ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionList) {
        this.announcementApprovedOpinionList = announcementApprovedOpinionList;
    }

    @Override
    public String toString() {
        return "AnnouncementDataJsonBean{" +
                "announcement=" + announcement +
                ", announcementApprovedOpinionList=" + announcementApprovedOpinionList +
                '}';
    }
}
