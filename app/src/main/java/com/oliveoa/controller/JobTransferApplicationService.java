package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.JobTransferApplicationApprovedOpinion;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JobTransferApplicationService {
    /**
     *  发起申请（）
     */
    public StatusAndMsgJsonBean submitApplication(String s, JobTransferApplication application, String members){
        DateFormat dateFormat = new DateFormat();
        System.out.println(members);

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("eid",application.getEid())
                    .add("aimdcid",application.getAimdcid())
                    .add("aimpcid",application.getAimpcid())
                    .add("reason",application.getAimpcid())
                    .add("approvedMember",members.toString())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.JOBTRANSFER_ADD)
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
    public StatusAndMsgJsonBean approveApplication(String s,JobTransferApplicationApprovedOpinion application){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("jtaid",application.getJtaid())
                    .add("isApproved", String.valueOf(application.getIsapproved()))
                    .add("opinion",application.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.JOBTRANSFER_APPROVED)
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
    public StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> getApplicationIapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.JOBTRANSFER_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> ap = gson.fromJson(json, type);

            System.out.println("ap = " +ap.toString());

            return ap;
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
    public StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> getApplicationIsubmited(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.JOBTRANSFER_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> ap = gson.fromJson(json, type);

            System.out.println("ap = " +ap.toString());

            return ap;
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
    public StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> getApplicationInfo(String s, String jtaid){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("jtaid",jtaid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.JOBTRANSFER_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean>>() {
            }.getType();
            StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> ap = gson.fromJson(json, type);

            System.out.println(" ap = " + ap.toString());
            return ap;
            } catch (IOException e) {
                //todo handler IOException
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
            return null;
    }

}
