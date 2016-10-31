package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Model.Currency;
import com.springapp.mvc.Service.ServiceImpl.InAllService;
import com.springapp.mvc.Service.ServiceImpl.LoanService;
import com.springapp.mvc.Service.ServiceImpl.MyFundService;
import com.springapp.mvc.Service.ServiceImpl.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/25.
 */
@Controller
@RequestMapping("inAll")
public class InAllController {
    private static Logger log = LoggerFactory.getLogger(FundController.class);

    private InAllService allService;
    private InAll all;

    private MyFundService myFundService;
    private LoanService loanService;
    private StockService stockService;

    @Autowired
    public void setAllService(InAllService allService){ this.allService = allService;}

    @Autowired
    public void setAll(InAll all){ this.all = all; }

    @Autowired
    public void setMyFundService(MyFundService myFundService){ this.myFundService = myFundService;}

    @Autowired
    public void setLoanService(LoanService loanService){ this.loanService = loanService;}

    @Autowired
    public void setStockService(StockService stockService){this.stockService = stockService;}

    @RequestMapping
    public ModelAndView view(){
        ModelAndView view = new ModelAndView("inAllDisplay");

        Map<String, BigDecimal> sumFund = myFundService.sumByRisk();
        Map<String, BigDecimal> sumStock = stockService.sumByRisk(Currency.RMB);
        Map<String, BigDecimal> sumLoan = loanService.sumByRisk();

        Map<AssetType, Map<String, BigDecimal>> typeRisk = new HashMap<AssetType, Map<String, BigDecimal>>();
        typeRisk.put(AssetType.FUND, sumFund);
        typeRisk.put(AssetType.LOAN, sumLoan);
        typeRisk.put(AssetType.STOCK, sumStock);

        Map<String, List<BigDecimal>> riskValues = new HashMap<String, List<BigDecimal>>();
        List<BigDecimal> list;
        for(AssetType key : typeRisk.keySet()){
            Map<String, BigDecimal> map = typeRisk.get(key);
            int index = 0;
            for(String risk : map.keySet()){
                if(key.equals(AssetType.FUND)){
                    index = 0;
                }else if(key.equals(AssetType.STOCK)){
                    index = 1;
                }else if(key.equals(AssetType.LOAN)){
                    index = 2;
                }

                if(riskValues.containsKey(risk)){
                    list = riskValues.get(risk);
                    list.set(index, map.get(risk).add(list.get(index)));
                }else{
                    list = new ArrayList<BigDecimal>();  //0:基金 1:证券 2：网贷
                    list.add(BigDecimal.ZERO);
                    list.add(BigDecimal.ZERO);
                    list.add(BigDecimal.ZERO);
                    list.set(index, map.get(risk));
                }
                riskValues.put(risk, list);
            }
        }

        BigDecimal riskInAll = BigDecimal.ZERO;
        List<BigDecimal> assetInAll = new ArrayList<BigDecimal>();
        assetInAll.add(BigDecimal.ZERO);
        assetInAll.add(BigDecimal.ZERO);
        assetInAll.add(BigDecimal.ZERO);
        for(String key : riskValues.keySet()){
            for(int i = 0; i < riskValues.get(key).size(); i++){
                BigDecimal value = riskValues.get(key).get(i);
                assetInAll.set(i, assetInAll.get(i).add(value));
            }
        }
        BigDecimal sumAll = BigDecimal.ZERO;
        for(BigDecimal b : assetInAll){
            sumAll = sumAll.add(b);
        }
//        assetInAll.add(sumAll);
        riskValues.put("总计", assetInAll);

        for(String key : riskValues.keySet()){
            riskInAll = BigDecimal.ZERO;
            for(BigDecimal b : riskValues.get(key)){
                riskInAll = riskInAll.add(b);
            }
            riskValues.get(key).add(riskInAll);
            riskValues.get(key).add(riskInAll.divide(sumAll, 4, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
        }
        view.addObject("riskValues", riskValues);

        Map<String, BigDecimal> sumUSA = stockService.sumByRisk(Currency.USA);
        sumUSA.put("总计",stockService.sum(Currency.USA));
        view.addObject("sumUSA", sumUSA);
        return view;
    }

    @RequestMapping(value = "calProfitRate", method = RequestMethod.POST)
    public String calProfitRate(HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = new Date();
        Date end = new Date();
        try{
            start = sdf.parse(request.getParameter("start"));
            end = sdf.parse(request.getParameter("end"));
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
        String[] type = request.getParameterValues("type");
//        String[] risk = request.getParameterValues("risk");

        allService.calProfitRate(start, end, type);
        return "redirect:/inAll";
    }
}
