package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.pojo.RecruitmentApplicationApprovedOpinion;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecruitmentApplicationService {
    /**
     *  发起申请（）
     */
    public StatusAndMsgJsonBean submitApplication(String s, RecruitmentApplicationItem application, String dcid, ArrayList<String> members){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("dcid",dcid)
                    .add("pcid",application.getPcid())
                    .add("number", String.valueOf(application.getNumber()))
                    .add("describe",application.getPositionDescribe())
                    .add("request",application.getPositionRequest())
                    .add("salary",application.getSalary())
                    .add("meetingMember",members.toString())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.RECRUITMENT_ADD)
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
    public StatusAndMsgJsonBean approveApplication(String s,RecruitmentApplicationApprovedOpinion application){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("raid",application.getRaid())
                    .add("isApproved", String.valueOf(application.getIsapproved()))
                    .add("opinion",application.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.RECRUITMENT_APPROVED)
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
    public StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> getApplicationIapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.RECRUITMENT_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> ap = gson.fromJson(json, type);

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
    public StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> getApplicationIsubmited(String s){
        try {

            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.RECRUITMENT_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            System.out.println("json = " +json);
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>>>() {}.getType();
            StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> ap = gson.fromJson(json,type);
           /* StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> ap = GsonUtil.fromJson(GsonUtil.toJson(response.body(),true),
                    new TypeToken<StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>>>() {});*/
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
    public StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> getApplicationInfo(String s, String raid){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("raid",raid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.RECRUITMENT_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean>>() {
            }.getType();
            StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> ap = gson.fromJson(json, type);

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
