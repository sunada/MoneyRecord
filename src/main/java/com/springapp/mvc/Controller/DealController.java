package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Service.FundService;
import com.springapp.mvc.Service.ServiceImpl.DealService;
import com.springapp.mvc.Service.ServiceImpl.MyFundService;
import com.springapp.mvc.Service.ServiceImpl.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
@Controller
@RequestMapping("/deal")
public class DealController {
    private Deal deal;
    private DealService dealService;
    private FundService fundService;
    private MyFundService myFundService;
    private MyFund myFund;
    private Stock stock;
    private StockService stockService;

    @Autowired
    public void setDeal(Deal deal) {this.deal = deal;}
    @Autowired
    public void setDealService(DealService dealService) { this.dealService = dealService;}
    @Autowired
    public void setFundService(FundService fundService){this.fundService = fundService;}
    @Autowired
    public void setMyFundService(MyFundService myFundService){ this.myFundService = myFundService;}
    @Autowired
    public void setMyFund(MyFund myFund){ this.myFund = myFund;}
    @Autowired
    public void setStock(Stock stock){ this.stock = stock;}
    @Autowired
    public void setStockService(StockService stockService){ this.stockService = stockService; }

    //查看交易记录
    @RequestMapping("fundList")
    public ModelAndView fundDeals(HttpServletRequest request){
        ModelAndView view = new ModelAndView("deals");

        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        List<Deal> list = dealService.readDeals(code, belongTo, "fund");

        view.addObject("deals", list);
        return view;
    }

    @RequestMapping("stockList")
    public ModelAndView stockDeals(HttpServletRequest request){
        ModelAndView view = new ModelAndView("deals");

        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        List<Deal> list = dealService.readDeals(code, belongTo, "stock");

        view.addObject("deals", list);
        return view;
    }

    //自动补充定投交易
    @RequestMapping("updateAipDeal")
    public String updateAipDeal(){
        ArrayList<Fund> funds = fundService.readFund(true);
        if (dealService.updateAipDeals(funds)){
            return "redirect:/fund";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping("delete")
    public String deleteDeal(HttpServletRequest request){
        int id = Integer.valueOf(request.getParameter("id"));
        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        BigDecimal share = new BigDecimal(request.getParameter("share"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        BigDecimal cost = new BigDecimal(request.getParameter("cost"));
        DealType dealType = DealType.valueOf(request.getParameter("dealType"));

        //删除基金交易数据
        myFund = myFundService.readMyFundByCB(code, belongTo);
        if(myFund != null) {
            if(dealType == DealType.FAIP || dealType == DealType.FBUY) {
                myFund.setShare(myFund.getShare().subtract(share));
                myFund.setCost(myFund.getCost().subtract(amount).add(cost));
            }else if(dealType == DealType.FREINVE){
                myFund.setShare(myFund.getShare().subtract(share));
            }else if(dealType == DealType.FREDEMP){
                myFund.setShare(myFund.getShare().add(share));
                myFund.setCost(myFund.getCost().add(amount).subtract(cost));
            }else if(dealType == DealType.FCASH){
                myFund.setCost(myFund.getCost().add(amount));
            }
            myFundService.updateMyFund(myFund);

            if (dealService.delete(id, "fund")) {
                return "redirect:/fund";    //belongTo为中文，放在url里传不过去，可能是chrome浏览器的原因？
            }
            return "redirect:/fund";
        }

        //删除证券交易
        stock = stockService.readStockByCB(code, belongTo);
        BigDecimal costAll;
        if(stock != null){
            BigDecimal dealAmount = new BigDecimal(request.getParameter("amount"));
            BigDecimal amountBefore = stock.getCost().multiply(stock.getShare()).add(dealAmount);
            stock.setShare(stock.getShare().subtract(share));
            if(stock.getShare().compareTo(BigDecimal.ZERO) > 0) {
                stock.setCost(amountBefore.divide(stock.getShare()));
            }else{
                stock.setCost(BigDecimal.ZERO);
            }

//            if(dealType == DealType.SSELL){
//                share = BigDecimal.ZERO.subtract(share);
//            }
//            stock.setCost(stockService.calStockCost(code, belongTo, price, share, dealType));

            stockService.update(stock);

            if(stock.getCost().compareTo(BigDecimal.ZERO) == 0){
                stockService.delete(stock.getCode());
            }

            if (dealService.delete(id, "stock")) {
                return "redirect:/stock";    //belongTo为中文，放在url里传不过去，可能是chrome浏览器的原因？
            }

            return "redirect:/stock";
        }
        return "redirect:/";
    }
}
