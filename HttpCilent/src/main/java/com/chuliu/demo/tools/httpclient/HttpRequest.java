package com.chuliu.demo.tools.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eiuhucl on 8/20/2018.
 * Send single http requests
 */
public class HttpRequest {

    //连接对象
    private HttpURLConnection conn;

    //Http访问的地址
    private String url;

    //Http的方式
    private Method method;

    //Http请求的请求主体
    private String requestBody;

    //代理
    private Proxy proxy;

    //超时
    private int timeout;

    //请求头信息
    private Map<String,String> headerPropertyMap;

    public String getUrl(){
        return this.url;
    }

    public Method getMethod(){
        return this.method;
    }

    public HttpRequest url(String url){
        this.url = url;
        return this;
    }

    public HttpRequest method(Method method){
        this.method = method;
        return this;
    }

    public HttpRequest timeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public int getTimeout(){
        return this.timeout;
    }

    public HttpRequest requestBody(String requestBody){
        this.requestBody = requestBody;
        return this;
    }

    public HttpRequest proxy(Proxy proxy){
        this.proxy = proxy;
        return this;
    }

    //调用此方法会完全覆盖原有的properties
    public HttpRequest setPropertiesByMap(Map<String,String> newPropertyMap){
        this.headerPropertyMap = newPropertyMap;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public HttpRequest addProperty(String key, String value){
        if (this.getProperties() == null) {
            setPropertiesByMap(new HashMap<String, String>());
        }
        headerPropertyMap.put(key,value);
        return this;
    }

    public Map<String,String> getProperties(){
        return this.headerPropertyMap;
    }

    HttpResponse getResponse(HttpURLConnection connection){

        return new HttpResponse(connection);
    }


    //建立连接并发送请求
    public HttpResponse execute(){

        //初始化连接数据
        initConnection();

        //发送（准备）
        sendRequest();

        //getInputStream()时，请求才真正被发送出去
        HttpResponse response = getResponse(conn);

        return response;
    }

    private void initConnection(){

        try {
            URL urlObject = new URL(getUrl());

            if (proxy == null) {
                conn = (HttpURLConnection)urlObject.openConnection();
            }else{
                conn = (HttpURLConnection)urlObject.openConnection(proxy);
            }

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);

            //当请求有http body的时候，需要设置成true，允许output
            if (this.getMethod() == Method.POST || this.getMethod() == Method.PUT) {
                // 设置是否往httpUrlConnection写数据，默认情况下是false;
                conn.setDoOutput(true);
                conn.setUseCaches(false);
            }

            //设置连接超时
            if (getTimeout() != 0) {
                conn.setConnectTimeout(getTimeout());
            }

            //设置请求的头属性，如contentType，accept等等
            for (String key : this.getProperties().keySet()) {
                conn.setRequestProperty(key,this.getProperties().get(key));
            }

            //设置请求方法
            conn.setRequestMethod(getMethod().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(){

        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //POST 和 PUT 可能有request body, GET和DELETE的request body直接忽略
        if (this.getMethod() == Method.POST || this.getMethod() == Method.PUT) {

            //如果有request body,则用输出流写出
            if (!"".equals(getRequestBody())) {
                try {
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(getRequestBody().getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    HttpRequest(){

    }



}
