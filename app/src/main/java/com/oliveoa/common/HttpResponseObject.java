package com.oliveoa.common;

public class HttpResponseObject<T> {
    private int status;
    private String msg;
    private T data;
    private String cookies;

    public HttpResponseObject() {
    }

    public HttpResponseObject(int status, String msg, T data, String cookies) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.cookies = cookies;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "HttpResponseObject{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", cookies='" + cookies + '\'' +
                '}';
    }
}
