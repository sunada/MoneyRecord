package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import com.springapp.mvc.Service.ServiceImpl.DealService;
import com.springapp.mvc.Service.ServiceImpl.HistoryAssetService;
import com.springapp.mvc.Service.ServiceImpl.LoanService;
import com.springapp.mvc.Service.ServiceImpl.StockService;
import com.springapp.mvc.Util.GetNet;
import com.springapp.mvc.Util.NetDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/24.
 */
@Controller
@RequestMapping("/stock")
public class StockController {
    private static Logger log = LoggerFactory.getLogger(StockController.class);

    private StockService stockService;
    private Stock stock;
    private HistoryAssetService historyAssetService;
    @Resource
    private DealService dealService;

    @Autowired
    public void setStockService(StockService stockService){ this.stockService = stockService;}

    @Autowired
    public void setStock(Stock stock){ this.stock = stock;}

    @Autowired
    public void setHistoryAssetService(HistoryAssetService historyAssetService){ this.historyAssetService = historyAssetService;}

    @RequestMapping
    public ModelAndView stockDisplay(){
        ModelAndView view = new ModelAndView("stockDisplay");
        ArrayList<Stock> stocks = stockService.read(Currency.RMB);
        view.addObject("stocks", stocks);

//        GetNet getNet = new GetNet();
//        view.addObject("exchangeRate", getNet.getExchangeRate(Currency.USA));

        ArrayList<Stock> useStocks = stockService.read(Currency.USA);
        view.addObject("useStocks", useStocks);

        Map<String, Date> accountNewDealDate = dealService.getAccountNewDealDate(AssetType.STOCK);
        view.addObject("accDate", accountNewDealDate);

        Map<String, List<BigDecimal>> map = stockService.addUp(stocks);
        view.addObject("group", map);

        BigDecimal rmbSum = stockService.sum(Currency.RMB);
        view.addObject("rmbSum", rmbSum);

        Map<String, BigDecimal> belongToSum = stockService.sumByBelongTo(Currency.RMB);
        view.addObject("belongToSum", belongToSum);

        List<HistoryAsset> historyAssets = historyAssetService.readHistory(AssetType.STOCK);
//        List<Stock> historyAssets = stockService.readHistory();
        view.addObject("historyAssets", historyAssets);

        Map<String, List<BigDecimal>> historyProfit = historyAssetService.getHistoryProfit(AssetType.STOCK);
        view.addObject("historyProfit", historyProfit);
        return view;
    }

    @RequestMapping("updateCurrent")
    public String updateStockNet(){
        ArrayList<Stock> stocks = stockService.read(Currency.RMB);
        stocks.addAll(stockService.read(Currency.USA));
        GetNet getNet = new GetNet();
        BigDecimal net;
        Map<String, Object> netMap = new HashMap<String, Object>();
        Map<String, Object> dateNet;

        for(Stock stock : stocks){
            if(stock.getShare().compareTo(BigDecimal.ZERO) <= 0){
                continue;
            }
            dateNet = getNet.getStockNet(stock.getCode());
            net = (BigDecimal)dateNet.get("net");
//            date = (String)dateNet.get("date");
            //若现价为0，则保留数据库里记录的现价
            if (net.compareTo(BigDecimal.ZERO) != 0  && !net.equals(stock.getCurrent())) {
                netMap.put("code", stock.getCode());
                netMap.put("current", net);
                stock.setAmount(net.multiply(stock.getShare()));
//                netMap.put("date", (String)dateNet.get("date"));  //数据库里未设置现价日期
                stockService.updateCurrent(netMap);
            }
        }
        return "redirect:/stock";
    }

    @RequestMapping("stockAdd")
    public String stockAdd(){ return "stockAdd";}

    @RequestMapping(value = "stockSave", method = RequestMethod.POST)
    public String stockSave(HttpServletRequest request){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String belongTo = request.getParameter("belongTo");
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));  //买入或卖出价
        BigDecimal share = new BigDecimal(request.getParameter("share"));
        DealType dealType = DealType.valueOf(request.getParameter("dealType"));

        BigDecimal current = BigDecimal.valueOf(0.00);

        stock.setCode(code);
        stock.setName(name);
        stock.setBelongTo(belongTo);
        stock.setCost(cost);
        stock.setCurrent(current);
        if(dealType == DealType.SSELL){
            share = BigDecimal.ZERO.subtract(share);
        }
        stock.setShare(share);
//        stock.setAmount(amount);
        stock.setRisk(Risk.valueOf(request.getParameter("risk")));

        if(belongTo.equals("积7XJ11330")){
            stock.setCurrency(Currency.USA);
        }else{
            stock.setCurrency(Currency.RMB);
        }

        log.debug("In StockController.save, {}", stock.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = sdf.parse(request.getParameter("date"));
            if (stockService.saveStock(stock, date, dealType, BigDecimal.valueOf(0))) {
                //return "redirect:/stock/stockAdd?code=" +code + "&name=" + name + "&belongTo=" + belongTo + "&cost=" + cost + "&dealType=SBUY";
                BigDecimal costByDeal = stockService.calDealCost(stock.getCode(), stock.getCost(), stock.getShare().abs(), dealType);
                BigDecimal amount = BigDecimal.ZERO;
                amount = stock.getCost().multiply(stock.getShare()).add(costByDeal);
                if(stockService.saveStockDeal(stock, date, costByDeal, amount, dealType)){
                    return "redirect:/stock";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "saveStockByFile", method = RequestMethod.POST)
    public String saveStockByFile(HttpServletRequest request){
        File file = new File(request.getParameter("strPath"));
        if(stockService.saveStockByFile(file)){
            return "redirect:/stock";
        }
        return "redirect:/";
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String belongTo = request.getParameter("belongTo");
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));
        BigDecimal share = new BigDecimal(request.getParameter("share"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        BigDecimal current = new BigDecimal(request.getParameter("current"));

        stock.setCode(code);
        stock.setName(name);
        stock.setBelongTo(belongTo);
        stock.setCost(cost);
        stock.setCurrent(current);
        stock.setShare(share);
        stock.setAmount(amount);
        stock.setRisk(Risk.valueOf(request.getParameter("risk")));

        log.debug("In LoansController.save, {}", stock.toString());
        if (stockService.update(stock)) {
            return "redirect:/stock";
        }else{
            return "redirect:/";
        }
    }


}
