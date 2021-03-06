package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeaveApplicationService {

    //发起请假申请(begintime,endtime,reason,type,normalrest,swaprest,shouldrest,supplementrest,approvedMember[])
    public StatusAndMsgJsonBean sentlapplication(String s,LeaveApplication leaveApplication,String approvedMember){
        DateFormat dateFormat = new DateFormat();
        String begintime =dateFormat.LongtoDate(leaveApplication.getBegintime());
        //begintime= begintime +":00";
        String endtime =dateFormat.LongtoDate(leaveApplication.getEndtime());
        //endtime = endtime +":00";
        System.out.println(begintime+"---"+endtime);
        System.out.println(approvedMember);
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("begintime",begintime)
                    .add("endtime",endtime)
                    .add("reason",leaveApplication.getReason())
                    .add("type", String.valueOf(leaveApplication.getType()))
                    .add("normalrest","")
                    .add("swaprest","")
                    .add("shouldrest","")
                    .add("supplementrest","")
                    .add("approvedMember",approvedMember)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_ADD)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);
            System.out.println("StatusAndMsgJsonBean = " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //审核请假申请(laid,isApproved,opinion)
    public StatusAndMsgJsonBean approvedapplication(String s, LeaveApplicationApprovedOpinionList la){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("laid",la.getLaid())
                    .add("isApproved", String.valueOf(la.getIsapproved()))
                    .add("opinion",la.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_APPROVED)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);
            System.out.println("StatusAndMsgJsonBean = " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取待我审核的请假申请
    public LeaveApplicationJsonBean getlapplicationunapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<LeaveApplicationJsonBean>() {
            }.getType();
            LeaveApplicationJsonBean leaveapplications = gson.fromJson(json, type);
            System.out.println("LeaveApplicationJsonBean = " + leaveapplications);

            return leaveapplications;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我已审核的请假申请
    public LeaveApplicationJsonBean getlapplicationapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_APPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<LeaveApplicationJsonBean>() {
            }.getType();
            LeaveApplicationJsonBean leaveapplications = gson.fromJson(json, type);
            System.out.println("LeaveApplicationJsonBean = " + leaveapplications);

            return leaveapplications;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我提交的请假申请
    public LeaveApplicationJsonBean getlapplicationsubmited(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<LeaveApplicationJsonBean>() {
            }.getType();
            LeaveApplicationJsonBean leaveApplications = gson.fromJson(json, type);
            System.out.println("LeaveApplicationJsonBean = " + leaveApplications);

            return leaveApplications;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取请假申请详情
    public LeaveApplicationHttpResponseObject getlapplicationinfo(String s,String laid){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("laid",laid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LAPPLICATION_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<LeaveApplicationHttpResponseObject>() {
            }.getType();
            LeaveApplicationHttpResponseObject lahro = gson.fromJson(json, type);
            System.out.println("LeaveApplicationHttpResponseObject = " + lahro);

            return lahro;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}
