package com.springapp.mvc.Util;

import com.springapp.mvc.Model.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/26.
 */
public class GetNet {
    private static Logger log = LoggerFactory.getLogger(GetNet.class);

    public BigDecimal getFundNet(String code, Date date){
        if(code.startsWith("-")){
            return BigDecimal.ONE;
        }
//    public BigDecimal getFundNet(String code,  String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(date);

        String url = "http://fund.eastmoney.com/f10/F10DataApi.aspx";
        String param = "type=lsjz&code=" + code +"&sdate=" + dateStr + "&edate=" + dateStr;
//        String param = "type=lsjz&code=" + code +"&sdate=" + date + "&edate=" + date;
        String s = HttpRequest.sendGet(url, param);
        BigDecimal net = new BigDecimal(0.00);
        if(!s.contains("暂无数据")) {
            String arr[] = s.split("<td class='tor bold'>");
            if(arr.length < 2){
                return net;
            }
            String netStr = arr[1].substring(0, 6);
//            String accNetStr = arr[2].substring(0, 6);
            net = new BigDecimal(netStr);
        }
        return net;
    }

    public Map<String, Object> getStockNet(String code){
        Map<String, Object> map = new HashMap<String, Object>();
        if(code.startsWith("-")){
            map.put("net", BigDecimal.ONE);
            return map;
        }
        if(code.startsWith("124") || code.startsWith("122")|| code.startsWith("112")){
            map.put("net", getBondPayPrice(code));
            return map;
        }
        String url = "http://hq.sinajs.cn/?list=";
        if(code.startsWith("6") || code.startsWith("510")|| code.startsWith("501")){
            code = "sh" + code;
        }else{
            code = "sz" + code;
        }
        url += code;
        String s = HttpRequest.sendGet(url, "");
        BigDecimal net = new BigDecimal(0.00);
        String date = "";
        String arr[] = s.split(",");
        if(arr.length > 3) {
            String netStr = arr[3];
//            date = arr[30];
            net = new BigDecimal(netStr);
        }

        map.put("net", net);
//        map.put("date", date);
        return map;
    }

    public BigDecimal getBondPayPrice(String code){
        String url = "https://www.jisilu.cn/data/bond/detail/" + code;
        BigDecimal res = BigDecimal.ZERO;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements tds = doc.getElementById("exbondtitle").select("table").select("td");
            String contents = tds.get(4).text();
            res = new BigDecimal(contents.substring(3));

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public BigDecimal getDanjuanNet(String code){
        String url = "https://danjuanapp.com/plan/CSI" + code;
        String res = HttpRequest.sendGet(url, "");
        String seq[] = res.split("<div class=\"data\">");
        BigDecimal net = BigDecimal.ZERO;
        if(seq.length == 3){
            String netStr = seq[2].split("</div>")[0];
            net = new BigDecimal(netStr);
        }else if(seq.length == 2){
            String netStr = seq[1].split("</div>")[0];
            net = new BigDecimal(netStr);
        }
        return net;
    }

    //请求结果回来太慢
    public BigDecimal getExchangeRate(Currency currency){
        String url = "http://www.usd-cny.com/";
        String res = HttpRequest.sendGet(url, "");
//        String res = "<TD HEIGHT=28 valign=\"middle\" bgcolor=\"#FFFFFF\">       <div align=\"center\"><!--?hbmc=美元&topic=\"><u--><a href=\"http://www.usd-cny.com/usd-rmb.htm\">美元 USD</u></a></div></TD>    <TD HEIGHT=15 align=\"center\" valign=\"middle\"><div align=\"right\">675.47&nbsp;</div></TD>    <TD HEIGHT=15 align=\"center\" valign=\"middle\"><div align=\"right\">669.93&nbsp;</div></TD>    <TD align=\"center\" valign=\"middle\"><div align=\"right\">678.04&nbsp;</div></TD>    <TD align=\"center\" valign=\"middle\"><div align=\"right\">678.04&nbsp;</div></TD> ";
        String seq[] = null;
        String tmp[] = null;
        if(currency.equals(Currency.USD)){
            seq = res.split("美元 USD");
        }
        if(seq.length == 0){
            return BigDecimal.ONE;
        }

        tmp = seq[1].split("TD>");
        if(tmp.length == 0){
            return BigDecimal.ONE;
        }
        seq = tmp[1].split("right");
        if(seq.length == 0){
            return BigDecimal.ONE;
        }
        String sub = seq[1].substring(2,8);
        return new BigDecimal(sub);
    }
}
