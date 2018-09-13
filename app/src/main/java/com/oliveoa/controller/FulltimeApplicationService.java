package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FulltimeApplicationService {
    /**
     *  发起申请（）
     */
    public StatusAndMsgJsonBean submitApplication(String s, FulltimeApplication application, String members){
        DateFormat dateFormat = new DateFormat();
        String begintime =dateFormat.LongtoDate(application.getBegintime());
        String endtime =dateFormat.LongtoDate(application.getEndtime());
        System.out.println(begintime+"---"+endtime);
        System.out.println(members);
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                   .add("begintime",begintime)
                    .add("endtime",endtime)
                    .add("personalSummary",application.getPersonalSummary())
                    .add("approvedMember",members)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.FULLTIME_ADD)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("statusAndMsgJsonBean = " +statusAndMsgJsonBean.toString());

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  审核申请（）
     */
    public StatusAndMsgJsonBean approveApplication(String s,FulltimeApplicationApprovedOpinion application){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("faid",application.getFaid())
                    .add("isApproved", String.valueOf(application.getIsapproved()))
                    .add("opinion",application.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.FULLTIME_APPROVED)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("statusAndMsgJsonBean = " +statusAndMsgJsonBean.toString());

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取待我审核申请（）
     */
    public StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> getApplicationIapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.FULLTIME_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> fa = gson.fromJson(json, type);

            System.out.println("fa = " +fa.toString());

            return fa;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取我提交的申请（）
     */
    public StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> getApplicationIsubmited(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.FULLTIME_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> fa = gson.fromJson(json, type);

            System.out.println("fa = " +fa.toString());

            return fa;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取申请详情（）
     */
    public StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> getApplicationInfo(String s, String faid){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("faid",faid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.FULLTIME_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean>>() {
            }.getType();
            StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> fa = gson.fromJson(json, type);

            System.out.println(" fa = " + fa.toString());

            return fa;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
