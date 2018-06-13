package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.MessageJsonbean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BusinessTripApplicationService {

    //发起出差申请(begintime,endtime,place,task,approvedMember[])
    public StatusAndMsgJsonBean sentbtapplication(String s,BusinessTripApplication businessTripApplication,ArrayList<String> approvedMember){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("begintime",dateFormat.LongtoDate(businessTripApplication.getBegintime()))
                    .add("endtime",dateFormat.LongtoDate(businessTripApplication.getEndtime()))
                    .add("place",businessTripApplication.getPlace())
                    .add("task",businessTripApplication.getTask())
                    .add("approvedMember",approvedMember.toString())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.BTAPPLICATION_ADD)
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

    //审核出差申请(btaid,isApproved,opinion)
    public StatusAndMsgJsonBean approvedbtapplication(String s, BusinessTripApplicationApprovedOpinionList btol){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("btaid",btol.getBtaid())
                    .add("isApproved", String.valueOf(btol.getIsapproved()))
                    .add("opinion",btol.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.BTAPPLICATION_APPROVED)
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

    //获取出差申请详情(btaid)
    public BusinessTripApplicationHttpResponseObject getbtapplicationinfo(String s,String btaid){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("btaid",btaid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.BTAPPLICATION_ALLSEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<BusinessTripApplicationHttpResponseObject>() {
            }.getType();
            BusinessTripApplicationHttpResponseObject btapplication = gson.fromJson(json, type);
            System.out.println("BusinessTripApplicationHttpResponseObject = " + btapplication);


            return btapplication;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取待我审核的出差申请
    public BusinessTripApplicationJsonBean getbtapplicationunapproved(String s){

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.BTAPPLICATION_APPROVED)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<BusinessTripApplicationJsonBean>() {
            }.getType();
            BusinessTripApplicationJsonBean btapplication = gson.fromJson(json, type);
            System.out.println("BusinessTripApplicationJsonBean = " + btapplication);


            return btapplication;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我提交的出差申请
    public BusinessTripApplicationJsonBean getbtapplicationsubmited(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.BTAPPLICATION_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<BusinessTripApplicationJsonBean>() {
            }.getType();
            BusinessTripApplicationJsonBean btapplication = gson.fromJson(json, type);
            System.out.println("BusinessTripApplicationJsonBean = " + btapplication);


            return btapplication;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}
