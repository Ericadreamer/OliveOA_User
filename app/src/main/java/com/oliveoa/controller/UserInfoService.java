package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.ContactInfo;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserInfoService {

    //获取个人信息
    public UserLoginJsonBean userinfo(String s) {
        try {
            //Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.USER_INFO_SEARCH)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            //Headers headers = response.headers();
            //Log.i("info_respons.headers",headers+"");

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<UserLoginJsonBean>() {
            }.getType();
            UserLoginJsonBean userloginJsonBean = gson.fromJson(json, type);
            System.out.println("userloginJsonBean = " + userloginJsonBean);

            return userloginJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //修改个人信息
    public StatusAndMsgJsonBean updateuserinfo(String s, ContactInfo userInfo) {

        try {
          //  Log.i("info_Login","知道了session："+s);
           // Log.d("updateinfo",userInfo.toString());
            FormBody body = new FormBody.Builder()
                    .add("eid",userInfo.getEid())
                    .add("dcid",userInfo.getDcid())
                    .add("pcid",userInfo.getPcid())
                    .add("id",userInfo.getId())
                    .add("name",userInfo.getName())
                    .add("sex",userInfo.getSex())
                    .add("birth",userInfo.getBirth())
                    .add("tel",userInfo.getTel())
                    .add("email",userInfo.getEmail())
                    .add("address",userInfo.getAddress())
                    .build();
           // Log.d("updateinfobody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.USER_INFO_UPDATE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusandmsgJsonBean = gson.fromJson(json, type);
            System.out.println("statusandmsgJsonBean = " + statusandmsgJsonBean);

            return statusandmsgJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //修改用户密码
    public StatusAndMsgJsonBean updatepassword(String s, String pwd) {

        try {
         //   Log.i("info_Login","知道了session："+s);
         //   Log.d("updatepwd",pwd);
            FormBody body = new FormBody.Builder()
                    .add("newPassword",pwd)
                    .build();
            //Log.d("updatepwdbody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.USER_PASSWORD_UPDATE)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusandmsgJsonBean = gson.fromJson(json, type);
            System.out.println("statusandmsgJsonBean = " + statusandmsgJsonBean);

            return statusandmsgJsonBean;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取通讯录
    public ContactHttpResponseObject contact(String s){
        try {
            //   Log.i("info_Login","知道了session："+s);
            //   Log.d("updatepwd",pwd);
            FormBody body = new FormBody.Builder()
                    .build();
            //Log.d("updatepwdbody",body.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.USER_CONTACT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);

            java.lang.reflect.Type type = new TypeToken<ContactHttpResponseObject>() {
            }.getType();
            ContactHttpResponseObject contacthttpresponseobject = gson.fromJson(json, type);
            System.out.println("contacthttpresponseobject = " + contacthttpresponseobject);

            return contacthttpresponseobject;

        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //根据部门dcid获取职务
    public DutyInfoJsonBean getPosition(String dcid){
          try {

                      OkHttpClient client = new OkHttpClient();
                      FormBody body = new FormBody.Builder()
                              .add("dcid",dcid)
                              .build();
          
                      Request request = new Request.Builder()
                              .url(Const.DUTY_SEARCH)
                              .post(body)
                              .build();
                      Response response = client.newCall(request).execute();
                      //System.out.println(response.body().string());
          
                      String json = response.body().string();
                      Gson gson = new Gson();
                      Type type = new TypeToken<DutyInfoJsonBean>() {
                      }.getType();
                       DutyInfoJsonBean dutyInfoJsonBean  = gson.fromJson(json, type);
          
                      System.out.println("dutyInfoJsonBean  = " +dutyInfoJsonBean);
          
                      return dutyInfoJsonBean;
                  } catch (IOException e) {
                      //todo handler IOException
                      //throw new RuntimeException(e);
                      e.printStackTrace();
           }
           return null;
    }
}
