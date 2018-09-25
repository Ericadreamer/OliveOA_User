package com.oliveoa.jsonbean;

import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;

import java.util.ArrayList;

public class FulltimeApplicationInfoJsonBean {
    private FulltimeApplication fulltimeApplication;
    private ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinionList;

    public FulltimeApplicationInfoJsonBean() {
    }

    public FulltimeApplicationInfoJsonBean(FulltimeApplication fulltimeApplication, ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinionList) {
        this.fulltimeApplication = fulltimeApplication;
        this.fulltimeApplicationApprovedOpinionList = fulltimeApplicationApprovedOpinionList;
    }

    public FulltimeApplication getFulltimeApplication() {
        return fulltimeApplication;
    }

    public void setFulltimeApplication(FulltimeApplication fulltimeApplication) {
        this.fulltimeApplication = fulltimeApplication;
    }

    public ArrayList<FulltimeApplicationApprovedOpinion> getFulltimeApplicationApprovedOpinionList() {
        return fulltimeApplicationApprovedOpinionList;
    }

    public void setFulltimeApplicationApprovedOpinionList(ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinionList) {
        this.fulltimeApplicationApprovedOpinionList = fulltimeApplicationApprovedOpinionList;
    }

    @Override
    public String toString() {
        return "FulltimeApplicationInfoJsonBean{" +
                "fulltimeApplication=" + fulltimeApplication +
                ", fulltimeApplicationApprovedOpinionList=" + fulltimeApplicationApprovedOpinionList +
                '}';
    }
}

