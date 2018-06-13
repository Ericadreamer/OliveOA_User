package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.jsonbean.OvertimeApplicationApprovedOpinionListInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OvertimeApplictionService {

    //发起加班申请(reason,begintime,endtime(str),approvedMember[])
    public StatusAndMsgJsonBean sentotapplication(String s,OvertimeApplication overtimeApplication,ArrayList<String> aeid){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("reason",overtimeApplication.getReason())
                    .add("begintime",dateFormat.LongtoDate(overtimeApplication.getBegintime()))
                    .add("endtime",dateFormat.LongtoDate(overtimeApplication.getEndtime()))
                    .add("approvedMember",aeid.toString())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s).url(Const.OTAPPLICATION_ADD).post(body).build();
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

    //审核加班申请(oaid,isApproved,opinion)
    public StatusAndMsgJsonBean approvedapplication(String s,OvertimeApplicationApprovedOpinionList overtimeApplicationApprovedOpinionList){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("oaid", overtimeApplicationApprovedOpinionList.getOaid())
                    .add("isApproved", String.valueOf(overtimeApplicationApprovedOpinionList.getIsapproved()))
                    .add("opinion",overtimeApplicationApprovedOpinionList.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s).url(Const.OTAPPLICATION_APPROVED).post(body).build();
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

    //获取加班申请详情(oaid)
    public OvertimeApplicationHttpResponseObject overtimeapplication(String s,String oaid){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("oaid", oaid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s).url(Const.OTAPPLICATION_ALL_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OvertimeApplicationHttpResponseObject >() {
            }.getType();
            OvertimeApplicationHttpResponseObject  oahro = gson.fromJson(json, type);

            System.out.println("OvertimeApplicationHttpResponseObject  = " + oahro);

            return oahro;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

        return null;
    }

    //获取待我审核的加班申请
    public OvertimeApplicationApprovedOpinionListInfoJsonBean unapprovedotapplication(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s).url(Const.OTAPPLICATION_NEEDAPPROVED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OvertimeApplicationApprovedOpinionListInfoJsonBean>() {
            }.getType();
            OvertimeApplicationApprovedOpinionListInfoJsonBean oa = gson.fromJson(json, type);

            System.out.println("OvertimeApplicationApprovedOpinionListInfoJsonBean = " + oa);

            return oa;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我提交的加班申请
    public OvertimeApplicationInfoJsonBean submitotapplication(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s).url(Const.OTAPPLICATION_SUBMIT_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<OvertimeApplicationInfoJsonBean>() {
            }.getType();
            OvertimeApplicationInfoJsonBean overtimeapplicationjsonbean = gson.fromJson(json, type);

            System.out.println("<OvertimeApplicationInfoJsonBean> = " + overtimeapplicationjsonbean);

            return overtimeapplicationjsonbean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
