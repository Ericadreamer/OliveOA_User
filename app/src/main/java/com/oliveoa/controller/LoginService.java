package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.ContactInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginService {
//    public static void main(String[] args){
//        HttpResponseObject<UserInfo> userinfo =login("001","001");
//        System.out.println(userinfo.toString());
//        LogoutJsonBean logoutJsonBean = logout("JSESSIONID=79FCCE1528F86852F380F1270E377939");
//        System.out.println(logoutJsonBean.toString());
//    }
    private String s;
    public HttpResponseObject<ContactInfo> login(String username, String password) {
        /**
         * 1.建立http连接
         * 2.传入参数
         * 3.获取数据接口传回的json
         * 4.解析json
         * 5.获取结果
         * 6.返回true or false
         */

        try {

            System.out.println(Const.User_LOGIN);
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("id", username)
                    .add("password", password)
                    .build();
            Request request = new Request.Builder().url(Const.User_LOGIN).post(body).build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

            Headers headers = response.headers();
            Log.i("info_respons.headers",headers+"");
            List<String> cookies = headers.values("Set-Cookie");
            String session = cookies.get(0);
            s = session.substring(0, session.indexOf(";"));
            System.out.println("info_respons.headers"+headers+"");

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<UserLoginJsonBean>() {
            }.getType();
            UserLoginJsonBean UserLoginJsonBean = gson.fromJson(json, type);

            System.out.println("UserLoginJsonBean = " + UserLoginJsonBean);
            HttpResponseObject httpResponseObject = new HttpResponseObject<>(UserLoginJsonBean.getStatus(), UserLoginJsonBean.getMsg(), UserLoginJsonBean.getData(),s);
            return httpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    public LogoutJsonBean logout(String s) {

        try {

            //Log.i("info_Login","知道了session："+s);
            FormBody body = new FormBody.Builder()
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.USER_LOGOUT)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            //Headers headers = response.headers();
            //Log.i("info_respons.headers",headers+"");

            String json = response.body().string();
            Gson gson = new Gson();
            System.out.println(json);
            java.lang.reflect.Type type = new TypeToken<LogoutJsonBean>() {
            }.getType();
            LogoutJsonBean logoutJsonBean = gson.fromJson(json, type);
            System.out.println("UserLogoutJsonBean = " + logoutJsonBean.getStatus());

            return logoutJsonBean;




        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}


