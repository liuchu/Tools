package com.chuliu.demo.tools.httpclient;

/**
 * Created by eiuhucl on 8/20/2018.
 * Support GET POST PUT and DELETE
 */
public enum Method {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private String value;

    private Method(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
