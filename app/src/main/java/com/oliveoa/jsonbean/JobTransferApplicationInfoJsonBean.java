package com.oliveoa.jsonbean;

import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.JobTransferApplicationApprovedOpinion;

import java.util.ArrayList;

public class JobTransferApplicationInfoJsonBean {
    private JobTransferApplication jobTransferApplication;
    private ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinionList;

    public JobTransferApplicationInfoJsonBean() {
    }

    public JobTransferApplicationInfoJsonBean(JobTransferApplication jobTransferApplication, ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinionList) {
        this.jobTransferApplication = jobTransferApplication;
        this.jobTransferApplicationApprovedOpinionList = jobTransferApplicationApprovedOpinionList;
    }

    public JobTransferApplication getJobTransferApplication() {
        return jobTransferApplication;
    }

    public void setJobTransferApplication(JobTransferApplication jobTransferApplication) {
        this.jobTransferApplication = jobTransferApplication;
    }

    public ArrayList<JobTransferApplicationApprovedOpinion> getJobTransferApplicationApprovedOpinionList() {
        return jobTransferApplicationApprovedOpinionList;
    }

    public void setJobTransferApplicationApprovedOpinionList(ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinionList) {
        this.jobTransferApplicationApprovedOpinionList = jobTransferApplicationApprovedOpinionList;
    }

    @Override
    public String toString() {
        return "JobTransferApplicationInfoJsonBean{" +
                "jobTransferApplication=" + jobTransferApplication +
                ", jobTransferApplicationApprovedOpinionList=" + jobTransferApplicationApprovedOpinionList +
                '}';
    }
}
