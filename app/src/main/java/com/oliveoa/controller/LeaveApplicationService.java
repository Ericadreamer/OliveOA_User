package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
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
    public StatusAndMsgJsonBean sentlapplication(String s,LeaveApplication leaveApplication,ArrayList<String> aeid ){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("begintime",dateFormat.LongtoDate(leaveApplication.getBegintime()))
                    .add("endtime",dateFormat.LongtoDate(leaveApplication.getBegintime()))
                    .add("reason",leaveApplication.getReason())
                    .add("type", String.valueOf(leaveApplication.getType()))
                    .add("normalrest",dateFormat.LongtoDate(leaveApplication.getNormalRest()))
                    .add("swaprest",dateFormat.LongtoDate(leaveApplication.getSwapRest()))
                    .add("shouldrest",dateFormat.LongtoDate(leaveApplication.getShouldRest()))
                    .add("supplementrest",dateFormat.LongtoDate(leaveApplication.getSupplementRest()))
                    .add("approvedMember",aeid.toString())
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
    public ArrayList<LeaveApplication> getlapplicationunapproved(String s){
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
            java.lang.reflect.Type type = new TypeToken<ArrayList<LeaveApplication>>() {
            }.getType();
            ArrayList<LeaveApplication> leaveapplications = gson.fromJson(json, type);
            System.out.println("ArrayList<LeaveApplication> = " + leaveapplications);

            return leaveapplications;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我提交的请假申请
    public ArrayList<LeaveApplication> getlapplicationsubmited(String s){
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
            java.lang.reflect.Type type = new TypeToken<ArrayList<LeaveApplication>>() {
            }.getType();
            ArrayList<LeaveApplication> leaveApplications = gson.fromJson(json, type);
            System.out.println("ArrayList<LeaveApplication> = " + leaveApplications);

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
