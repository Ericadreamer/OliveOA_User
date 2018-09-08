package com.oliveoa.jsonbean;

import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.JobTransferApplicationApprovedOpinion;

import java.util.ArrayList;

public class JobTransferApplicationInfoJsonBean {
    private JobTransferApplication jobTransferApplication;
    private ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinions;

    public JobTransferApplicationInfoJsonBean() {
    }

    public JobTransferApplicationInfoJsonBean(JobTransferApplication jobTransferApplication, ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinions) {
        this.jobTransferApplication = jobTransferApplication;
        this.jobTransferApplicationApprovedOpinions = jobTransferApplicationApprovedOpinions;
    }

    public JobTransferApplication getJobTransferApplication() {
        return jobTransferApplication;
    }

    public void setJobTransferApplication(JobTransferApplication jobTransferApplication) {
        this.jobTransferApplication = jobTransferApplication;
    }

    public ArrayList<JobTransferApplicationApprovedOpinion> getJobTransferApplicationApprovedOpinions() {
        return jobTransferApplicationApprovedOpinions;
    }

    public void setJobTransferApplicationApprovedOpinions(ArrayList<JobTransferApplicationApprovedOpinion> jobTransferApplicationApprovedOpinions) {
        this.jobTransferApplicationApprovedOpinions = jobTransferApplicationApprovedOpinions;
    }

    @Override
    public String toString() {
        return "JobTransferApplicationInfoJsonBean{" +
                "jobTransferApplication=" + jobTransferApplication +
                ", jobTransferApplicationApprovedOpinions=" + jobTransferApplicationApprovedOpinions +
                '}';
    }
}
