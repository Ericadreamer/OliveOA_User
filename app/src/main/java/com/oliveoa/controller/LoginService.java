package com.oliveoa.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.UserInfo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginService {
    public HttpResponseObject<UserInfo> login( String username,String password) {
        /**
         * 1.建立http连接
         * 2.传入参数
         * 3.获取数据接口传回的json
         * 4.解析json
         * 5.获取结果
         * 6.返回true or false
         */

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder().url(Const.User_LOGIN).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            Headers headers = response.headers();
            Log.i("info_respons.headers",headers+"");

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<UserLoginJsonBean>() {
            }.getType();
            UserLoginJsonBean companyLoginJsonBean = gson.fromJson(json, type);
            //CompanyLoginJsonBean companyLoginJsonBean = new CompanyLoginJsonBean(-1,"nothing",null);
            System.out.println("companyLoginJsonBean = " + companyLoginJsonBean);
            HttpResponseObject<UserInfo> httpResponseObject = new HttpResponseObject<>(companyLoginJsonBean.getStatus(), companyLoginJsonBean.getMsg(), companyLoginJsonBean.getData());
            return httpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }
}


