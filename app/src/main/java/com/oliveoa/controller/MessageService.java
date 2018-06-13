package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.jsonbean.MessageJsonbean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageService {

    //获取发送给我的消息（orderby）
    public MessageJsonbean getMessagetome(String s,int orderby){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderby))
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.MESSAGE_TOME)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());


            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MessageJsonbean>() {
            }.getType();
           MessageJsonbean message = gson.fromJson(json, type);

            System.out.println("message = " + message);
            return message;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

        return null;

    }

    //获取我发送的信息（orderby）
    public MessageJsonbean getMessagebyme(String s,int orderby){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderby))
                    .build();

            Request request = new Request.Builder().url(Const.MESSAGE_BYME).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<MessageJsonbean>() {
            }.getType();
            MessageJsonbean messageJsonbean = gson.fromJson(json, type);

            System.out.println("MessageJsonbean = " + messageJsonbean);

            return messageJsonbean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }


        return null;

    }

    //发送消息（eid，msg）
    public StatusAndMsgJsonBean sentMessage(String s,String eid,String msg){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("eid", eid)
                    .add("msg",msg)
                    .build();

            Request request = new Request.Builder().url(Const.MESSAGE_SENT).post(body).build();
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
}
