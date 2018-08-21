package com.chuliu.demo.tools.httpclient;

import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by eiuhucl on 8/20/2018.
 */
public class HttpResponse {

    private HttpURLConnection connection;

    protected HttpResponse(HttpURLConnection connection){
        this.connection = connection;

        
    }

    public int getStatusCode(){
        return 200;
    }

    public String getResponseBodyString(){
        return "";
    }
}
