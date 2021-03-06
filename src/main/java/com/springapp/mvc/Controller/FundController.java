package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Service.FundService;
import com.springapp.mvc.Service.ServiceImpl.DealService;
import com.springapp.mvc.Service.ServiceImpl.MyFundService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/fund")
public class FundController {
    private static Logger log = LoggerFactory.getLogger(FundController.class);

    private FundService fundService;
    private Fund fund;
    private MyFundService myFundService;
    private MyFund myFund;
    private DealService dealService;

    @Autowired
    public void setFundService(FundService fundService){
        this.fundService = fundService;
    }

    @Autowired
    public void setFund(Fund fund) {this.fund = fund;}

    @Autowired
    public void setMyFundService(MyFundService myFundService){ this.myFundService = myFundService; }

    @Autowired
    public void setMyFund(MyFund myFund){ this.myFund = myFund; }

    @Autowired
    public void setDealService(DealService dealService){ this.dealService = dealService; }

    //基金一览
    @RequestMapping
    public ModelAndView fundDisplay(){
        ModelAndView view = new ModelAndView("fundDisplay");
        ArrayList<MyFund> funds;

        funds = myFundService.readMyFund();
        view.addObject("funds", funds);

        //select code, name,share,cost,risk, belong_to from myfunds where share <= 0 order by code;
        ArrayList<MyFund> hisFunds = myFundService.readMyHistoryFund();
        BigDecimal cost = BigDecimal.ZERO;
        for(MyFund fund : hisFunds){
            cost = dealService.sumCost(fund.getCode(), "fund");  //赎回实得
            fund.setProfit(BigDecimal.ZERO.subtract(fund.getCost()));  //赎回时的盈利
            fund.setCost(cost.subtract(fund.getProfit()));          //成本+盈利=实得
        }
        view.addObject("hisFunds", hisFunds);

        BigDecimal sum = myFundService.sum();
        view.addObject("sum", sum);

        Map<String, List<BigDecimal>> map = myFundService.addUp(funds);
        view.addObject("groupByRisk", map);

        Map<String, List<BigDecimal>> belongTos = myFundService.addUpByBelongTo(funds);
        view.addObject("belongTos", belongTos);

        Map<String, List<BigDecimal>> historyProfit = myFundService.getHistoryProfit();
        view.addObject("historyProfit", historyProfit);

        Date date = myFundService.getLatestPictureDate();
        view.addObject("latestDate", date);
        return  view;
    }

    //更新基金净值
    @RequestMapping(value = "updateFundNet", method = RequestMethod.GET)
    public String updateFundNet(){
        ArrayList<MyFund> funds;
        funds = myFundService.readMyFund();

        for(MyFund fund : funds) {
            myFundService.updateNet(fund);
        }
        return "redirect:/fund";
    }

    //买入基金
    @RequestMapping(value = "myFundAdd", method = RequestMethod.GET)
    public String myFundAdd() {
        return "myFundAdd";
    }

    //保存买入或卖出的基金信息
    @RequestMapping(value = "saveMyFund", method = RequestMethod.POST)
    public String save(HttpServletRequest request){
        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        String name = request.getParameter("name");

        // for fund_deal
        DealType dealType = DealType.valueOf(request.getParameter("dealType"));
        BigDecimal share = BigDecimal.ZERO;
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal net = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.ZERO;
        if(dealType == DealType.FBUY) {
            share = new BigDecimal(request.getParameter("share"));
            cost = new BigDecimal(request.getParameter("cost"));
            net = new BigDecimal(request.getParameter("net"));
            amount = new BigDecimal(request.getParameter("amount"));
            amount = BigDecimal.ZERO.subtract(amount);
        }else if(dealType == DealType.FREDEMP) {
            share = new BigDecimal(request.getParameter("fredemp_share"));
            cost = new BigDecimal(request.getParameter("fredemp_cost"));
            net = new BigDecimal(request.getParameter("fredemp_net"));
            amount = new BigDecimal(request.getParameter("fredemp_amount"));
        }else if(dealType == DealType.FREINVE){
            share = new BigDecimal(request.getParameter("freinve_share"));
        }else{
            amount = new BigDecimal(request.getParameter("cash_amount"));
        }

        //update fund_deals
        Deal deal = new Deal();
        deal.setCode(code);
        deal.setBelongTo(belongTo);
        deal.setName(name);
        deal.setNet(net);
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = sdf.parse(request.getParameter("date"));
            deal.setDate(date);
            deal.setDateReal(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(dealType == DealType.FBUY){
//            BigDecimal prate = new BigDecimal(request.getParameter("prate"));
//            BigDecimal prateCal = prate.divide(BigDecimal.valueOf(100));
//            BigDecimal clean = amount.divide(prateCal.add(BigDecimal.ONE),2,BigDecimal.ROUND_HALF_EVEN);
//            deal.setShare(clean.divide(net,2, BigDecimal.ROUND_HALF_EVEN));
//            deal.setCost(amount.subtract(clean));
            deal.setShare(share);
            deal.setCost(cost);
//            amount = amount.add(cost);
        }else if(dealType == DealType.FREINVE){
            deal.setShare(share);
            deal.setCost(BigDecimal.ZERO);
        }else if(dealType == DealType.FCASH){
            deal.setShare(BigDecimal.ZERO);
            deal.setCost(BigDecimal.ZERO);
        }else if(dealType == DealType.FREDEMP){
//            BigDecimal rrate = new BigDecimal(request.getParameter("rrate"));
//            BigDecimal reFee = net.multiply(share).multiply(rrate).divide(BigDecimal.valueOf(100));
//            deal.setCost(reFee);
            deal.setCost(cost);
            deal.setShare(share);
        }
        deal.setAmount(amount);
        deal.setDealType(dealType);
       dealService.updateFundDeal(deal);

        MyFund myFundSql = myFundService.readMyFundByCB(code, belongTo);
        if(myFundSql == null){
            log.debug("In FundsController.save, {}", fund.toString());
            //insert a new fund to myfunds
            myFund.setCode(code);
            myFund.setName(name);
            myFund.setShare(share);
            myFund.setNet(net);
//            myFund.setCost(cost);
            myFund.setCost(amount.multiply(new BigDecimal(-1))); //此处的cost为持有成本，而非单次交易成本
            myFund.setDate(new Date());
            myFund.setBelongTo(belongTo);
            myFund.setPurchaseRate(new BigDecimal(request.getParameter("prate")));
            myFund.setRedemptionRate(new BigDecimal(request.getParameter("rrate")));
            myFund.setChargeMode(ChargeMode.valueOf(request.getParameter("chargeMode")));
            myFund.setDividendMode(DividendMode.valueOf(request.getParameter("dividendMode")));
            myFund.setRisk(Risk.valueOf(request.getParameter("risk")));
            if (myFundService.saveFund(myFund)) {
                return "redirect:/fund";
            }else{
                return "redirect:/";
            }
        }else{
            if(dealType == DealType.FBUY){
                myFundSql.setShare(share.add(myFundSql.getShare()));
                myFundSql.setCost(amount.multiply(new BigDecimal(-1)).add(myFundSql.getCost()));
                myFundSql.setPurchaseRate(new BigDecimal(request.getParameter("prate")));
            }else if(dealType == DealType.FREINVE){
                myFundSql.setShare(share.add(myFundSql.getShare()));
            }else if(dealType == DealType.FREDEMP){
                myFundSql.setShare(myFundSql.getShare().subtract(share));
                myFundSql.setCost(myFundSql.getCost().subtract(amount));
//                myFundSql.setCost(myFundSql.getCost().subtract(amount).add(cost));
//                myFundSql.setCost(myFundSql.getCost().add(amount));
            }else if(dealType == DealType.FCASH){
                myFundSql.setCost(myFundSql.getCost().subtract(amount));
            }
            if(myFundService.updateMyFund(myFundSql)){
                return "redirect:/fund";
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value="myFundUpdate", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        BigDecimal share = new BigDecimal(request.getParameter("share"));
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));

        myFund.setCode(code);
        myFund.setName(name);
        myFund.setShare(share);
        myFund.setCost(cost);
        myFund.setDate(new Date());
//        myFund.setPurchaseRate(new BigDecimal(request.getParameter("prate")));
//        myFund.setRedemptionRate(new BigDecimal(request.getParameter("rrate")));
//        myFund.setChargeMode(ChargeMode.valueOf(request.getParameter("chargeMode")));
//        myFund.setDividendMode(DividendMode.valueOf(request.getParameter("dividendMode")));
        myFund.setRisk(Risk.valueOf(request.getParameter("risk")));
        myFund.setBelongTo(request.getParameter("belongTo"));

        log.debug("In FundsController.save, {}", myFund.toString());
        if (myFundService.updateMyFund(myFund)) {
            return "redirect:/fund";
        }else{
            return "redirect:/";
        }
    }

    //定投基金计划一览
    @RequestMapping(value = "aipDisplay", method = RequestMethod.GET)
    public ModelAndView aipDisplay(){
        ModelAndView view = new ModelAndView("aipDisplay");
        ArrayList<Fund> fundValid;
        ArrayList<Fund> fundNotValid = new ArrayList<Fund>();
        boolean valid = true;
        fundValid = fundService.readFund(valid);
        view.addObject("fundValid", fundValid);

        fundNotValid = fundService.readFund(false);
        view.addObject("fundNotValid", fundNotValid);

        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        String str;
        BigDecimal amount;
        BigDecimal tmp;
        int times;
        BigDecimal sum = new BigDecimal(0.00);
        for(Fund fund : fundValid){
            str = fund.getRisk().getName();
            if(fund.getInterval() == Interval.ONEWEEK) {
                times = 4;
            }else if(fund.getInterval() == Interval.TWOWEEKS){
                times = 2;
            }else{
                times = 1;
            }
            tmp = fund.getAmount().multiply(new BigDecimal(times));
            sum = sum.add(tmp);
            if(map.containsKey(str)) {
                amount = map.get(str).add(tmp);
            }else{
                amount = tmp;
            }
            map.put(str, amount);
        }
        view.addObject("group", map);
        view.addObject("sum", sum);

        return  view;
    }

    //新增基金定投计划
    @RequestMapping(value = "aipAdd", method = RequestMethod.GET)
    public String printWelcome() {
        return "aipAdd";
    }

    //新增保存基金定投
    @RequestMapping(value = "saveAip", method = RequestMethod.POST)
    public String aip(HttpServletRequest request){
        fund.setCode(request.getParameter("code"));
        fund.setName(request.getParameter("name"));
        fund.setAmount(new BigDecimal(request.getParameter("amount")));
        fund.setBelongTo(request.getParameter("belongTo"));
        fund.setPurchaseRate(new BigDecimal(request.getParameter("prate")));
        fund.setRedemptionRate(new BigDecimal(request.getParameter("rrate")));
        fund.setRisk(Risk.valueOf(request.getParameter("risk")));
        fund.setChargeMode(ChargeMode.valueOf(request.getParameter("chargeMode")));
        fund.setDividendMode(DividendMode.valueOf(request.getParameter("dividendMode")));
        String date = request.getParameter("startTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fund.setStartTime(sdf.parse(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        fund.setValid(true);

        Interval interval = Interval.valueOf(request.getParameter("interval"));
        fund.setInterval(interval);

        //每隔1周或2周的某个星期 或每隔每固定某天
        if ( interval.equals(Interval.ONEWEEK) || interval.equals(Interval.TWOWEEKS)){
            Week week = Week.valueOf(request.getParameter("tmpDate"));
            fund.setWeek(week);
            fund.setDate(-1);
        }else if (interval.equals(Interval.MONTH)){
            fund.setDate(Integer.parseInt(request.getParameter("tmpDate")));
            fund.setWeek(Week.valueOf("EXC"));
        }else{
            return "redirect:/";
        }

        if (fundService.saveFund(fund)){
            return "redirect:/fund/aipDisplay";
        }else{
            return "redirect:/";
        }
    }

    //更新定投计划
    @RequestMapping(value = "updateAip", method = RequestMethod.POST)
    public String updateAip(HttpServletRequest request){
        fund.setCode(request.getParameter("code"));
        fund.setName(request.getParameter("name"));
        fund.setAmount(new BigDecimal(request.getParameter("amount")));
        fund.setBelongTo(request.getParameter("belongTo"));
        if(request.getParameter("prate") != null) {
            fund.setPurchaseRate(new BigDecimal(request.getParameter("prate")));
            fund.setRedemptionRate(new BigDecimal(request.getParameter("rrate")));
        }else{
            fund.setPurchaseRate(BigDecimal.ZERO);
            fund.setRedemptionRate(BigDecimal.ZERO);
        }
        fund.setRisk(Risk.valueOf(request.getParameter("risk")));
        fund.setChargeMode(ChargeMode.valueOf(request.getParameter("cMode")));
        if(request.getParameter("dMode") != null) {
            fund.setDividendMode(DividendMode.valueOf(request.getParameter("dMode")));
        }
        fund.setValid(Boolean.valueOf(request.getParameter("valid")));
        Interval interval = Interval.valueOf(request.getParameter("interval"));
        String date = request.getParameter("startTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fund.setStartTime(sdf.parse(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        fund.setInterval(interval);
        if(interval.equals(Interval.MONTH)){
            Pattern p = Pattern.compile("\\d+");
            Matcher m=p.matcher(request.getParameter("time"));
            if(m.find()) {
                String time = m.group();
                fund.setDate(Integer.valueOf(time));
            }
            fund.setWeek(Week.EXC);
        }else{
            fund.setWeek(Week.valueOf(request.getParameter("time")));
            fund.setDate(-1);
        }

        if (fundService.updateAip(fund)){
            return "redirect:/fund";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping(value = "picture", method = RequestMethod.POST)
    public String picture(HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(request.getParameter("date"));
            if (myFundService.picture(date)) {
                return "redirect:/fund";
            }else{
                return "redirect:/";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
    }
}