package com.springapp.mvc.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/7.
 */
public enum HuGangTongStocks {
    INSTANCE;
    Map<String, String> stocks;

    private HuGangTongStocks(){
        stocks = getStocksFromWeb();
    }

    public Map<String, String> getStocks(){
        return stocks;
    }

    public Map<String, String> getStocksFromWeb(){
        Map<String, String> stocks = null;
        String url = "http://www.sse.com.cn/services/hkexsc/disclo/eligible/";
        String html = HttpRequest.sendGet(url,"");
        Document doc = Jsoup.parse(html);
        return stocks;
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

