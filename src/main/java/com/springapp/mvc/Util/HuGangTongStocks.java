package com.springapp.mvc.Util;

import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/2/7.
 */
public enum HuGangTongStocks {
    INSTANCE;
    Map<String, String> stocks;

    @Resource
    private SqlSession sqlSession;

    private HuGangTongStocks(){
        while(stocks == null || stocks.isEmpty() || stocks.size() < 10) {
            stocks = getStocksFromWeb();
        }
    }

    public Map<String, String> getStocks(){
        return stocks;
    }

    public Map<String, String> getStocksFromWeb(){
        stocks = new HashMap<String, String>();
        String url = "http://www.sse.com.cn/services/hkexsc/disclo/eligible/";
//        String html = HttpRequest.sendGet(url,"");
//        Document doc = Jsoup.parse(html);
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url).openStream(), "UTF8", url);
            String tmp = doc.data().split(";")[20];
            tmp = tmp.substring(33);
            JSONObject json = JSONObject.fromObject(tmp);
            String list = json.getString("list");
            String[] seps = json.getString("list").split("]");

            for (String s : seps) {
                String code = s.replaceAll("[^(0-9)]*","");
                Pattern p =  Pattern.compile("([\\u4e00-\\u9fa5]+)");
                Matcher m = p.matcher(s);
                if (m.find()) {
                    String name = m.group(0);
                    stocks.put(name, code);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("code", code);
                    map.put("name", name);
                    sqlSession.update("Deals.insertHuGangTong", map);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            return stocks;
        }
//        return stocks;
    }

    public Map<String, String> getStocksFromSql(){
        return null;
    }
}


//public class HuGangTong{
//    private static HuGangTong instance;
//    private Map<String, String> map;
//
//    private HuGangTong(){
//        map = new HashMap<String, String>();
//    }
//
//    public static HuGangTong getInstance(){
//        if(instance == null) return new HuGangTong();
//        return instance;
//    }
//
//    public Map<String, String> getMap(){
//        return map;
//    }
//
//    public void setMap(Map<String, String> map){
//        this.map = map;
//    }
//}

