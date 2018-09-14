package com.chuliu.demo.tools.httpclient;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by eiuhucl on 8/20/2018.
 * Send single http requests
 */
public class HttpClient {

    private static final Logger logger = Logger.getLogger("HttpResponse");

    public static HttpRequest createRequest(){
        return new HttpRequest();
    }

    public static HttpResponse call(HttpRequest request){

        //检验request必要的属性是否被设置
        if (!validateRequestObject(request)){
            logger.severe("Request object is not settled correctly!");
        }

        //调用发送请求
        HttpResponse response = request.execute();

        return response;
    }

    private static boolean validateRequestObject(HttpRequest request){
        String url = request.getUrl();
        Method method = request.getMethod();

        return !"".equals(url) && !"".equals(method.toString());

    }




}
