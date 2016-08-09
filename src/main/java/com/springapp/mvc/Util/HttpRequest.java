package com.springapp.mvc.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/4/26.
 */
public class HttpRequest {
    private static Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public static String sendGet(String url, String param){
        String result = "";
        BufferedReader in = null;
        try{
            String urlName;
            if(param.equals("")){
                urlName = url;
            }else {
                urlName = url + "?" + param;
            }
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.connect();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                result += line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        finally {
            try{
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
