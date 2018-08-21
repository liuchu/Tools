package com.chuliu.demo.tools.httpclient;

import java.util.Map;

/**
 * Created by eiuhucl on 8/20/2018.
 * Send single http requests
 */
public class HttpClient {

    public static HttpRequest createRequest(){
        return new HttpRequest();
    }

    public static HttpResponse call(HttpRequest request){

        //检验request必要的属性是否被设置
        if (!validateRequestObject()){
            System.out.println("Request object is not settled correctly!");
        }

        //调用发送请求
        request.execute();

        return null;
    }

    private static boolean validateRequestObject(){
        return true;
    }

    public static void main(String[] args) {

        HttpRequest request = HttpClient.createRequest().url("http://www.baidu.com").method(Method.GET)
                .requestBody("");

        HttpResponse response = HttpClient.call(request);

        if (response != null) {
            int httpStatus = response.getStatusCode();

            String responseBody = response.getResponseBodyString();

            System.out.println(httpStatus+responseBody);
        }

    }


}
