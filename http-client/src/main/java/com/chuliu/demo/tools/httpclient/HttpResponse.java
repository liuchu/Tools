package com.chuliu.demo.tools.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eiuhucl on 8/20/2018.
 */
public class HttpResponse {

    private final Logger logger = Logger.getLogger("HttpResponse");

    private int statusCode;
    private String responseBodyString;

    private HttpURLConnection connection;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseBodyString() {
        return responseBodyString;
    }

    public void setResponseBodyString(String responseBodyString) {
        this.responseBodyString = responseBodyString;
    }

    protected HttpResponse(HttpURLConnection connection){
        this.connection = connection;
        initResponse();
    }

    private void initResponse(){

        try {
            setStatusCode(connection.getResponseCode());
            //connection.getRequestProperties();
            //connection.getHeaderFields();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            //状态码300以下，获取InputStream，300及以上获取ErrorStream
            if (getStatusCode() < StatusCode.HTTP_MULT_CHOICE) {

                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            //Construct buffer reader
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer sb = new StringBuffer();
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            //获取response返回的主体
            setResponseBodyString(sb.toString());
        } catch (IOException e) {
            logger.severe("Failed to get inputStream or read inputStream to String");
            e.printStackTrace();
        } finally {
            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.severe("Failed to close inputStream.");
                    e.printStackTrace();
                }
            }
        }

        //connection.getResponseCode();
    }


}
