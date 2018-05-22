package com.oliveoa.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpReqUtil {
    /**
     * get方式请求
     * @param url
     * @return 服务器返回值
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = new OkHttpClient().newCall(request).execute();
        if(!response.isSuccessful())
            throw new IOException("请求有错：" + response);
        return response;

    }

    /**
     * post 方式请求
     *
     * @param url
     * @param body 参数，已json方式传参
     * @return 服务器返回值
     * @throws IOException
     */
    public static Response post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder().url(url)
                .post(body).build();
        Response response = new OkHttpClient().newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("请求有错：" + response);
        return response;
    }
}
