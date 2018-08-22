package com.chuliu.demo.tools.httpclient;

import java.util.List;
import java.util.Map;

/**
 * Created by eiuhucl on 8/22/2018.
 */
public class Test {

    public static void printMap(Map<String,List<String>> map){

        for (String str : map.keySet()) {
            System.out.println("KEY:"+str);

            for (String val : map.get(str)) {
                System.out.print(val+": ");
            }
            System.out.println("");
            System.out.println("----------------");
        }
    }

    public static void main(String[] args) {

        /*HttpRequest request = HttpClient.createRequest().url("https://www.baidu.com").method(Method.GET).timeout(6000).
                addProperty(Property.CONTENT_TYPE,"text/html").addProperty(Property.CONNECTION,"keep-alive");

        HttpResponse response = HttpClient.call(request);

        if (response != null) {
            int httpStatus = response.getStatusCode();

            String responseBody = response.getResponseBodyString();

            System.out.println(httpStatus+responseBody);
        }*/

        String url = "http://localhost:8081/nbi/deliverysession?session_id=1";
        String requestBody = "username=chu&age=18";
        HttpRequest request1 = HttpClient.createRequest().url(url).method(Method.POST).timeout(2000)
                .addProperty(Property.CONTENT_TYPE,"text/plain")
                .requestBody(requestBody);
        HttpResponse response1 = HttpClient.call(request1);

        if (response1 != null) {
            int httpStatus = response1.getStatusCode();

            String responseBody = response1.getResponseBodyString();

            System.out.println(httpStatus+responseBody);
        }
    }
}
