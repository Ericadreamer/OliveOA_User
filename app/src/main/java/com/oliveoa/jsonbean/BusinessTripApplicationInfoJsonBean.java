package com.oliveoa.jsonbean;

import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;

import java.util.ArrayList;

public class BusinessTripApplicationInfoJsonBean {
    private BusinessTripApplication businessTripApplication;
    private ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionList;

    public BusinessTripApplicationInfoJsonBean() {
    }

    public BusinessTripApplicationInfoJsonBean(BusinessTripApplication businessTripApplication, ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists) {
        this.businessTripApplication = businessTripApplication;
        this.businessTripApplicationApprovedOpinionList= businessTripApplicationApprovedOpinionLists;
    }

    public BusinessTripApplication getBusinessTripApplication() {
        return businessTripApplication;
    }

    public void setBusinessTripApplication(BusinessTripApplication businessTripApplication) {
        this.businessTripApplication = businessTripApplication;
    }

    public ArrayList<BusinessTripApplicationApprovedOpinionList> getBusinessTripApplicationApprovedOpinionLists() {
        return businessTripApplicationApprovedOpinionList;
    }

    public void setBusinessTripApplicationApprovedOpinionLists(ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists) {
        this.businessTripApplicationApprovedOpinionList= businessTripApplicationApprovedOpinionLists;
    }

    @Override
    public String toString() {
        return "BusinessTripApplicationInfoJsonBean{" +
                "businessTripApplication=" + businessTripApplication +
                ", businessTripApplicationApprovedOpinionLists=" + businessTripApplicationApprovedOpinionList+
                '}';
    }
}
