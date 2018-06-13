package com.oliveoa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.IssueWorkHttpResponseObject;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.util.DateFormat;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WorkDetailService {

    //发布工作给下属（members,content,begintime,endtime）
    public StatusAndMsgJsonBean managework(ArrayList<String> members, String s, IssueWork issueWork){
        try {
            DateFormat dateFormat =new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("members", members.toString())
                    .add("content",issueWork.getContent())
                    .add("begintime",dateFormat.LongtoDate(issueWork.getBegintime()))
                    .add("endtime",dateFormat.LongtoDate(issueWork.getEndtime()))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_ASSIGING).post(body).build();
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

    //员工提交工作（aeid,content,begintime,endtime）
    public StatusAndMsgJsonBean submitwork(String s,WorkDetail workDetail){
        try {
            DateFormat dateFormat = new DateFormat();
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("aeid", workDetail.getAeid())
                    .add("content",workDetail.getContent())
                    .add("begintime",dateFormat.LongtoDate(workDetail.getBegintime()))
                    .add("endtime",dateFormat.LongtoDate(workDetail.getEndtime()))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_STAFFSUBMIT).post(body).build();
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

    //批阅工作（swid,isApproved,opinion）
    public StatusAndMsgJsonBean approvedwork(String s,WorkDetail workDetail){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("swid", workDetail.getSwid())
                    .add("isApproved", String.valueOf(workDetail.getIsapproved()))
                    .add("opinion",workDetail.getOpinion())
                    .build();

            Request request = new Request.Builder().url(Const.WORK_APPROVING).post(body).build();
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

    //获取待我批阅的工作（orderBy）
    public ArrayList<WorkDetail> getworkunapproved(String s,int orderBy){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderBy))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_APPROVED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ArrayList<WorkDetail>>() {
            }.getType();
            ArrayList<WorkDetail> workDetails = gson.fromJson(json, type);

            System.out.println("ArrayList<WorkDetail> = " +  workDetails);

            return  workDetails;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我发布的工作详情（orderBy）
    public ArrayList<IssueWork> getIssuework(String s,int orderBy){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderBy))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_ASSIGED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ArrayList<IssueWork>>() {
            }.getType();
            ArrayList<IssueWork> issueWorks = gson.fromJson(json, type);

            System.out.println("ArrayList<IssueWork> = " + issueWorks);

            return issueWorks;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取我提交的工作（orderBy）
    public ArrayList<WorkDetail> getsubmitedwork(String s,int orderBy){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderBy))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_SUBMITED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ArrayList<WorkDetail>>() {
            }.getType();
            ArrayList<WorkDetail> workDetails = gson.fromJson(json, type);

            System.out.println("ArrayList<WorkDetail> = " + workDetails);

            return workDetails;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取提交工作详情（swid）
    public IssueWorkHttpResponseObject getworkdetail(String s,String swid){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("swid", swid)
                    .build();

            Request request = new Request.Builder().url(Const.WORK_INFOSUBMITED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<IssueWorkHttpResponseObject>() {
            }.getType();
            IssueWorkHttpResponseObject iwhro = gson.fromJson(json, type);

            System.out.println("IssueWorkHttpResponseObject = " + iwhro);

            return iwhro;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    //获取提交给我的所有工作（orderby）
    public ArrayList<WorkDetail> getallsubmitworktome(String s,int orderBy){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("orderBy", String.valueOf(orderBy))
                    .build();

            Request request = new Request.Builder().url(Const.WORK_ALL_SUBMITED_SEARCH).post(body).build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<ArrayList<WorkDetail>>() {
            }.getType();
            ArrayList<WorkDetail> workDetails = gson.fromJson(json, type);

            System.out.println("ArrayList<WorkDetail> = " + workDetails);

            return workDetails;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

}
