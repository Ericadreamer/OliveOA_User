package com.oliveoa.jsonbean;

import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.pojo.RecruitmentApplicationApprovedOpinion;
import com.oliveoa.pojo.RecruitmentApplicationItem;

import java.util.ArrayList;

public class RecruitmentApplicationInfoJsonBean {
    private RecruitmentApplication recruitmentApplication;
    private RecruitmentApplicationItem recruitmentApplicationItem;
    private ArrayList<RecruitmentApplicationApprovedOpinion> recruitmentApplicationApprovedOpinions;

    public RecruitmentApplicationInfoJsonBean() {
    }

    public RecruitmentApplicationInfoJsonBean(RecruitmentApplication recruitmentApplication, RecruitmentApplicationItem recruitmentApplicationItem, ArrayList<RecruitmentApplicationApprovedOpinion> recruitmentApplicationApprovedOpinions) {
        this.recruitmentApplication = recruitmentApplication;
        this.recruitmentApplicationItem = recruitmentApplicationItem;
        this.recruitmentApplicationApprovedOpinions = recruitmentApplicationApprovedOpinions;
    }

    public RecruitmentApplication getRecruitmentApplication() {
        return recruitmentApplication;
    }

    public void setRecruitmentApplication(RecruitmentApplication recruitmentApplication) {
        this.recruitmentApplication = recruitmentApplication;
    }

    public RecruitmentApplicationItem getRecruitmentApplicationItem() {
        return recruitmentApplicationItem;
    }

    public void setRecruitmentApplicationItem(RecruitmentApplicationItem recruitmentApplicationItem) {
        this.recruitmentApplicationItem = recruitmentApplicationItem;
    }

    public ArrayList<RecruitmentApplicationApprovedOpinion> getRecruitmentApplicationApprovedOpinions() {
        return recruitmentApplicationApprovedOpinions;
    }

    public void setRecruitmentApplicationApprovedOpinions(ArrayList<RecruitmentApplicationApprovedOpinion> recruitmentApplicationApprovedOpinions) {
        this.recruitmentApplicationApprovedOpinions = recruitmentApplicationApprovedOpinions;
    }

    @Override
    public String toString() {
        return "RecruitmentApplicationInfoJsonBean{" +
                "recruitmentApplication=" + recruitmentApplication +
                ", recruitmentApplicationItem=" + recruitmentApplicationItem +
                ", recruitmentApplicationApprovedOpinions=" + recruitmentApplicationApprovedOpinions +
                '}';
    }
}
