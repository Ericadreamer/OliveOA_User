package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.Application;
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
public class MeetingApplicationService {

    /**
     *  发起申请（）
     */
    public StatusAndMsgJsonBean submitApplication(String s, MeetingApplication application, ArrayList<String> members){
          try {
                      DateFormat dateFormat = new DateFormat();
                      OkHttpClient client = new OkHttpClient();
                      FormBody body = new FormBody.Builder()
                              .add("theme",application.getTheme())
                              .add("begintime",dateFormat.LongtoDatemm(application.getBegintime()))
                              .add("endtime",dateFormat.LongtoDatemm(application.getEndtime()))
                              .add("place",application.getPlace())
                              .add("aeid",application.getAeid())
                              .add("meetingMember",members.toString())
                              .build();

                      Request request = new Request.Builder()
                              .addHeader("Cookie",s)
                              .url(Const.MEETING_ADD)
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
    public StatusAndMsgJsonBean approveApplication(String s, MeetingApplication application){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("maid",application.getMaid())
                    .add("isApproved", String.valueOf(application.getIsapproved()))
                    .add("opinion",application.getOpinion())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_APPROVED)
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
    public StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> getApplicationIapproved(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_NEEDAPPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> ma = gson.fromJson(json, type);

            System.out.println("ma = " +ma.toString());

            return ma;
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
    public StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> getApplicationIsubmited(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_SUBMIT_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> ma = gson.fromJson(json, type);

            System.out.println("ma = " +ma.toString());

            return ma;
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
    public StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> getApplicationInfo(String s,String maid ){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("maid",maid)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_ALL_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean>>() {
            }.getType();
            StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> ma = gson.fromJson(json, type);

            System.out.println(" meeting = " + ma.toString());

            return ma;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取我即将参加的会议（）
     */
    public StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> getApplicationIwilldo(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_WILLDO_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> ma = gson.fromJson(json, type);

            System.out.println("ma = " +ma.toString());

            return ma;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取我参加过的会议
     */
    public StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> getApplicationIdone(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_DONE_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> ma = gson.fromJson(json, type);

            System.out.println("ma = " +ma.toString());

            return ma;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取我正在参与的会议
     */
    public StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> getApplicationIdoing(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MEETING_DOING_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>>>() {
            }.getType();
            StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> ma = gson.fromJson(json, type);

            System.out.println("ma = " +ma.toString());

            return ma;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
