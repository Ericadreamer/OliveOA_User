package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  ClassName： MeetingApplicationService
 *  Description： 会议申请
 *  @Author： Erica
 */
public class LeaveOfficeApplicationService {

    /**
     *  发起申请（）
     */
    public StatusAndMsgJsonBean submitApplication(String s, LeaveOfficeApplication application,String members){
        DateFormat dateFormat = new DateFormat();
        String leavedate =dateFormat.LongtoDate(application.getLeavetime());
          try {
              System.out.println(leavedate);
              System.out.println(members);
              System.out.println(application.toString());

                      OkHttpClient client = new OkHttpClient();
                      FormBody body = new FormBody.Builder()
                              .add("leavetime",leavedate)
                              .add("handoverMatters",application.getHandoverMatters())
                              .add("reason",application.getReason())
                              .add("approvedMember",members)
                              .build();
                      System.out.println(body.size());
                      Request request = new Request.Builder()
                              .addHeader("Cookie",s)
                              .url(Const.LEAVEOFFICE_ADD)
                              .post(body)
                              .build();
                     /* System.out.println(request.toString());
                      System.out.println(request.url());*/
                      Response response = client.newCall(request).execute();

                      String json = response.body().string();
                      System.out.println(json);
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
    public StatusAndMsgJsonBean approveApplication(String s, LeaveOfficeApplicationApprovedOpinion application){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("loaid",application.getLoaid())
                    .add("isApproved", String.valueOf(application.getIsapproved()))
                    .add("opinion",application.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LEAVEOFFICE_APPROVED)
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
    public StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> getApplicationIapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LEAVEOFFICE_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> la = gson.fromJson(json, type);

            System.out.println("la = " +la.toString());

            return la;
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
    public StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> getApplicationIsubmited(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LEAVEOFFICE_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> la = gson.fromJson(json, type);

            System.out.println("la = " +la.toString());

            return la;
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
    public StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> getApplicationInfo(String s, String loaid){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("loaid",loaid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.LEAVEOFFICE_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean>>() {
            }.getType();
            StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> la = gson.fromJson(json, type);

            System.out.println(" la = " + la.toString());

            return la;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
