package com.oliveoa.controller;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.pojo.OfficialDocumentIssued;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 *  ClassName： DocumentService
 *  Description： 公文流转
 *  @Author： Erica
 */
public class DocumentService {
    private Request req = null;
    /**
     *  拟稿（）
     */
   public StatusAndMsgJsonBean documentDraft(String s,OfficialDocument officialDocument,File file){
         System.out.println(Const.DOCUMENTFLOW_DRAFT);
       try {
                     OkHttpClient client = new OkHttpClient();
                     //表单上传
                     MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

                     if(file != null){
                         System.out.println(file.getName());
                         System.out.println(file.getPath());
                         //MadiaType.parse()里面是上传的文件类型
                       //application/octet-stream代表不限类型，可以传入任何类型的文件
                         MediaType mediaType=MediaType.parse("application/octet-stream");
                         RequestBody filebody= RequestBody.create(mediaType,file);

                         RequestBody body = new MultipartBody.Builder()
                                 .setType(MultipartBody.ALTERNATIVE)
                                 .addFormDataPart("title",officialDocument.getTitle())
                                 .addFormDataPart("content", officialDocument.getContent())
                                 .addFormDataPart("nuclearDraftEid", officialDocument.getNuclearDraftEid())
                                 .addFormDataPart("issuedEid", officialDocument.getIssuedEid())
                                 .addFormDataPart("file",file.getName(),filebody)
                                 .build();
                         req = new Request.Builder()
                                 .addHeader("Cookie",s)
                                 .url(Const.DOCUMENTFLOW_DRAFT)
                                 .post(body)
                                 .build();
                         Response response = client.newCall(req).execute();
                         //System.out.println(response.body().string());

                         String json = response.body().string();
                         System.out.println(json);
                         Gson gson = new Gson();
                         Type type = new TypeToken<StatusAndMsgJsonBean>() {
                         }.getType();
                         StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

                         System.out.println(" StatusAndMsgJsonBean = " + statusAndMsgJsonBean);

                         return statusAndMsgJsonBean;
                     }else {
                         System.out.println("FORMBODY");
                         RequestBody filebody= RequestBody.create(null,"");
                         RequestBody body = new MultipartBody.Builder()
                                 .setType(MultipartBody.ALTERNATIVE)
                                 .addFormDataPart("title",officialDocument.getTitle())
                                 .addFormDataPart("content", officialDocument.getContent())
                                 .addFormDataPart("nuclearDraftEid", officialDocument.getNuclearDraftEid())
                                 .addFormDataPart("issuedEid", officialDocument.getIssuedEid())
                                 .addFormDataPart("file","",filebody)
                                 .build();
                         req = new Request.Builder()
                                 .addHeader("Cookie",s)
                                 .url(Const.DOCUMENTFLOW_DRAFT)
                                 .post(body)
                                 .build();
                         Response response = client.newCall(req).execute();
                         //System.out.println(response.body().string());

                         String json = response.body().string();
                         System.out.println(json);
                         Gson gson = new Gson();
                         Type type = new TypeToken<StatusAndMsgJsonBean>() {
                         }.getType();
                         StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

                         System.out.println(" StatusAndMsgJsonBean = " + statusAndMsgJsonBean);

                         return statusAndMsgJsonBean;
                     }


                 } catch (IOException e) {
                     //todo handler IOException
                     //throw new RuntimeException(e);
                     e.printStackTrace();
          }
          return null;
   }

    /**
     *  获取我拟稿的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentIDraft(String s){

          try {

                      OkHttpClient client = new OkHttpClient();
                      FormBody body = new FormBody.Builder()
                              .build();
          
                      Request request = new Request.Builder()
                              .addHeader("Cookie",s)
                              .url(Const.DOCUMENTFLOW_IDRAFTED_SEARCH)
                              .post(body)
                              .build();
                      Response response = client.newCall(request).execute();
                      //System.out.println(response.body().string());
          
                      String json = response.body().string();
                      Gson gson = new Gson();
                      Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
                      }.getType();
              StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);
          
                      System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);
          
                      return statusAndMsgAndDataHttpResponseObject;
                  } catch (IOException e) {
                      //todo handler IOException
                      //throw new RuntimeException(e);
                      e.printStackTrace();
           }
           return null;
    }

    /**
     *  获取待核稿的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentNeedNuclear(String s){

          try {
                      OkHttpClient client = new OkHttpClient();
                      FormBody body = new FormBody.Builder()
                              .build();

                      Request request = new Request.Builder()
                              .addHeader("Cookie",s)
                              .url(Const.DOCUMENTFLOW_NEEDNUCLEAR)
                              .post(body)
                              .build();
                      Response response = client.newCall(request).execute();
                      //System.out.println(response.body().string());

                      String json = response.body().string();
                      Gson gson = new Gson();
              Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
              }.getType();
              StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

              System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

              return statusAndMsgAndDataHttpResponseObject;
                  } catch (IOException e) {
                      //todo handler IOException
                      //throw new RuntimeException(e);
                      e.printStackTrace();
           }
           return null;
    }

    /**
     *  核稿（）
     */
    public StatusAndMsgJsonBean documentNuclear(String s,OfficialDocument officialDocument){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("isApproved", String.valueOf(officialDocument.getNuclearDraftIsapproved()))
                    .add("oponion", officialDocument.getNuclearDraftOpinion())
                    .add("odid", officialDocument.getOdid())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_NUCLEAR)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.string());

            String json = response.body().string();
            System.out.println(json);
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("StatusAndMsgJsonBean= " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取已经核稿的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentNucleared(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_NUCLEARED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取待签发的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentNeedIssue(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_NEEDISSUE_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  签发（）
     */
    public StatusAndMsgJsonBean documentIssue(String s,OfficialDocument officialDocument,String departments){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("isApproved", String.valueOf(officialDocument.getIssuedIsapproved()))
                    .add("oponion", officialDocument.getIssuedOpinion())
                    .add("odid", officialDocument.getOdid())
                    .add("departments", departments)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_ISSUE)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("StatusAndMsgJsonBean= " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取已经签发的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentIssued(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_ISSUEED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取待签收的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentNeedReceive(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_NEEDRECEIVE_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }



    /**
     *  签收（）
     */
    public StatusAndMsgJsonBean documentReceive(String s,String odid ,OfficialDocumentIssued officialDocumentIssued,String members){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("isReceive", String.valueOf(officialDocumentIssued.getIsreceive()))
                    .add("odid", odid)
                    .add("members",members)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_RECEIVE)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("StatusAndMsgJsonBean= " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取已经签收的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentIssued>> getdocumentReceived(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_RECEIVEED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentIssued>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentIssued>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentIssued>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取待办理的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentNeedRead(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_NEEDREAD_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  办理公文（）
     */
    public StatusAndMsgJsonBean documentRead(String s,String odid,OfficialDocumentCirculread officialDocumentCirculread){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .add("odid", odid)
                    .add("report", officialDocumentCirculread.getReport())
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_READ)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgJsonBean>() {
            }.getType();
            StatusAndMsgJsonBean statusAndMsgJsonBean = gson.fromJson(json, type);

            System.out.println("StatusAndMsgJsonBean= " + statusAndMsgJsonBean);

            return statusAndMsgJsonBean;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  获取已经办理的公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> getdocumentReaded(String s){
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody body = new FormBody.Builder()
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Cookie",s)
                    .url(Const.DOCUMENTFLOW_READED_SEARCH)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());

            String json = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> >() {
            }.getType();
            StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = gson.fromJson(json, type);

            System.out.println("StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>= " + statusAndMsgAndDataHttpResponseObject);

            return statusAndMsgAndDataHttpResponseObject;
        } catch (IOException e) {
            //todo handler IOException
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  获取公文详情（）
     */
    public StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> getdocumentInfo(String s ,String aid){

      try {

                  OkHttpClient client = new OkHttpClient();
                  FormBody body = new FormBody.Builder()
                          .add("odid",aid)
                          .build();

                  Request request = new Request.Builder()
                          .addHeader("Cookie",s)
                          .url(Const.DOCUMENTFLOW_GETDETAILS)
                          .post(body)
                          .build();
                  Response response = client.newCall(request).execute();
                  //System.out.println(response.body().string());

                  String json = response.body().string();
                  Gson gson = new Gson();
                  Type type = new TypeToken<StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean>>() {
                  }.getType();
          StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> statusAndMsgAndDataHttpResponseObject  = gson.fromJson(json, type);

                  System.out.println(" StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> = " +  statusAndMsgAndDataHttpResponseObject);

                  return statusAndMsgAndDataHttpResponseObject;
              } catch (IOException e) {
                  //todo handler IOException
                  //throw new RuntimeException(e);
                  e.printStackTrace();
       }
       return null;
    }

    /**
     *  获取全部公文（）
     */
    public StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> getalldocument(){

        return null;
    }

    /**
     *  下载文件接口（）
     */
    public StatusAndMsgJsonBean documentDownload(){

        return null;
    }
}
