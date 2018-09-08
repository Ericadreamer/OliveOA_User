package com.oliveoa.jsonbean;

import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;

import java.util.ArrayList;

public class FulltimeApplicationInfoJsonBean {
    private FulltimeApplication fulltimeApplication;
    private ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinions;

    public FulltimeApplicationInfoJsonBean() {
    }

    public FulltimeApplicationInfoJsonBean(FulltimeApplication fulltimeApplication, ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinions) {
        this.fulltimeApplication = fulltimeApplication;
        this.fulltimeApplicationApprovedOpinions = fulltimeApplicationApprovedOpinions;
    }

    public FulltimeApplication getFulltimeApplication() {
        return fulltimeApplication;
    }

    public void setFulltimeApplication(FulltimeApplication fulltimeApplication) {
        this.fulltimeApplication = fulltimeApplication;
    }

    public ArrayList<FulltimeApplicationApprovedOpinion> getFulltimeApplicationApprovedOpinions() {
        return fulltimeApplicationApprovedOpinions;
    }

    public void setFulltimeApplicationApprovedOpinions(ArrayList<FulltimeApplicationApprovedOpinion> fulltimeApplicationApprovedOpinions) {
        this.fulltimeApplicationApprovedOpinions = fulltimeApplicationApprovedOpinions;
    }

    @Override
    public String toString() {
        return "FulltimeApplicationInfoJsonBean{" +
                "fulltimeApplication=" + fulltimeApplication +
                ", fulltimeApplicationApprovedOpinions=" + fulltimeApplicationApprovedOpinions +
                '}';
    }
}

