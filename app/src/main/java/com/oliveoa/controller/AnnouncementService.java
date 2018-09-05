package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.StatusJsonBean;

import java.lang.reflect.Type;

import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnnouncementService {



    /**
     *   MethodName： get_annoucementinfo
     *   Description： 获取公告详情
     *   @Author： Erica
     */
     public StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> get_annoucementinfo(String s, String aid){
           try {
                       DateFormat dateFormat = new DateFormat();
                       OkHttpClient client = new OkHttpClient();
                       FormBody body = new FormBody.Builder()
                               .add("aid",aid)
                               .build();

                       Request request = new Request.Builder()
                               .addHeader("Cookie",s)
                               .url(Const.ANNOUNCEMENT_INFO)
                               .post(body)
                               .build();
                       Response response = client.newCall(request).execute();
                       //System.out.println(response.body().string());

                       String json = response.body().string();
                       Gson gson = new Gson();
                       Type type = new TypeToken<StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean>>() {
                       }.getType();
                       StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> announcementHttpResponseObject  = gson.fromJson(json, type);

                       System.out.println(" StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> = " +announcementHttpResponseObject );

                       return announcementHttpResponseObject;
                   } catch (IOException e) {
                       //todo handler IOException
                       //throw new RuntimeException(e);
                       e.printStackTrace();
            }
            return null;
     }


    /**
     *   MethodName： approved_annoucements
     *   Description： 审核公告
     *   @Author： Erica
     */
     public StatusJsonBean approved_annoucements(String s, AnnouncementInfo announcementInfo){
           try {
                       DateFormat dateFormat = new DateFormat();
                       OkHttpClient client = new OkHttpClient();
                       FormBody body = new FormBody.Builder()
                               .add("aid",announcementInfo.getAid())
                               .add("isApproved", String.valueOf(announcementInfo.getIsapproved()))
                               .add("opinion",announcementInfo.getContent())
                               .build();

                       Request request = new Request.Builder()
                               .addHeader("Cookie",s)
                               .url(Const.ANNOUNCEMENT_APPROVED)
                               .post(body)
                               .build();
                       Response response = client.newCall(request).execute();
                       //System.out.println(response.body().string());

                       String json = response.body().string();
                       Gson gson = new Gson();
                       Type type = new TypeToken<StatusJsonBean>() {
                       }.getType();
                       StatusJsonBean statusJsonBean = gson.fromJson(json, type);

                       System.out.println("StatusJsonBean = " + statusJsonBean);

                       return statusJsonBean;
                   } catch (IOException e) {
                       //todo handler IOException
                       //throw new RuntimeException(e);
                       e.printStackTrace();
            }
            return null;
     }

    /**
     *   MethodName： submit_annoucements
     *   Description： 提交公告
     *   @Author： Erica
     */
     public StatusAndMsgJsonBean submit_annoucements(String s,AnnouncementInfo announcementInfo,ArrayList<String> approvedMember){
           try {
                       DateFormat dateFormat = new DateFormat();
                       OkHttpClient client = new OkHttpClient();
                       FormBody body = new FormBody.Builder()
                               .add("title",announcementInfo.getTitle())
                               .add("content",announcementInfo.getContent())
                               .add("signature",announcementInfo.getSignature())
                               .add("publishtime",dateFormat.LongtoDate(announcementInfo.getPublishtime()))
                               .add("approvedMember", String.valueOf(approvedMember))
                               .build();

                       Request request = new Request.Builder()
                               .addHeader("Cookie",s)
                               .url(Const.ANNOUNCEMENT_SUBMITED)
                               .post(body)
                               .build();
                       Response response = client.newCall(request).execute();
                       //System.out.println(response.body().string());

                       String json = response.body().string();
                       Gson gson = new Gson();
                       Type type = new TypeToken<StatusAndMsgJsonBean>() {
                       }.getType();
                       StatusAndMsgJsonBean statusAndMsgJsonBean  = gson.fromJson(json, type);

                       System.out.println("StatusAndMsgJsonBean= " +statusAndMsgJsonBean.toString() );

                       return statusAndMsgJsonBean;
                   } catch (IOException e) {
                       //todo handler IOException
                       //throw new RuntimeException(e);
                       e.printStackTrace();
            }
            return null;
     }

    /**
     *   MethodName： get_published_annoucements
     *   Description： 获取已经发布的公告
     *   @Author： Erica
     */
    public AnnouncementJsonBean get_published_annoucements(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.ANNOUNCEMENT_PUBLISHED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<AnnouncementJsonBean>() {
            }.getType();
            AnnouncementJsonBean announcementJsonBean  = gson.fromJson(json, type);

            System.out.println(" AnnouncementJsonBean = " +announcementJsonBean );

            return announcementJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *   MethodName： get_approvedannoucement
     *   Description： 获取待我审核的公告
     *   @Author： Erica
     */
    public AnnouncementJsonBean  get_approvedannoucement(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.ANNOUNCEMENT_APPROVED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<AnnouncementJsonBean>() {
            }.getType();
            AnnouncementJsonBean announcementJsonBean  = gson.fromJson(json, type);

            System.out.println(" AnnouncementJsonBean = " +announcementJsonBean );

            return announcementJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *   MethodName： get_isubmitannoucements
     *   Description： 获取我提交的公告
     *   @Author： Erica
     */
    public AnnouncementJsonBean  get_isubmitannoucements(String s){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.ANNOUNCEMENT_ISUBMITED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<AnnouncementJsonBean>() {
            }.getType();
            AnnouncementJsonBean announcementJsonBean  = gson.fromJson(json, type);

            System.out.println(" AnnouncementJsonBean = " +announcementJsonBean );

            return announcementJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


}
