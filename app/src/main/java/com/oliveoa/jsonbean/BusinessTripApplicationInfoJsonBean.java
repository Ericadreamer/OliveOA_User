package com.oliveoa.jsonbean;

import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;

import java.util.ArrayList;

public class BusinessTripApplicationInfoJsonBean {
    private ArrayList<BusinessTripApplication> businessTripApplications;
    private ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists;

    public BusinessTripApplicationInfoJsonBean() {
    }

    public BusinessTripApplicationInfoJsonBean(ArrayList<BusinessTripApplication> businessTripApplications, ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists) {
        this.businessTripApplications = businessTripApplications;
        this.businessTripApplicationApprovedOpinionLists = businessTripApplicationApprovedOpinionLists;
    }

    public ArrayList<BusinessTripApplication> getBusinessTripApplications() {
        return businessTripApplications;
    }

    public void setBusinessTripApplications(ArrayList<BusinessTripApplication> businessTripApplications) {
        this.businessTripApplications = businessTripApplications;
    }

    public ArrayList<BusinessTripApplicationApprovedOpinionList> getBusinessTripApplicationApprovedOpinionLists() {
        return businessTripApplicationApprovedOpinionLists;
    }

    public void setBusinessTripApplicationApprovedOpinionLists(ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists) {
        this.businessTripApplicationApprovedOpinionLists = businessTripApplicationApprovedOpinionLists;
    }

    @Override
    public String toString() {
        return "BusinessTripApplicationInfoJsonBean{" +
                "businessTripApplications=" + businessTripApplications +
                ", businessTripApplicationApprovedOpinionLists=" + businessTripApplicationApprovedOpinionLists +
                '}';
    }
}
