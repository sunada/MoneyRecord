package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.InAll;
import com.springapp.mvc.Model.Loan;
import com.springapp.mvc.Model.Risk;
import com.springapp.mvc.Model.Stock;
import com.springapp.mvc.Service.ServiceImpl.InAllService;
import com.springapp.mvc.Service.ServiceImpl.LoanService;
import com.springapp.mvc.Service.ServiceImpl.MyFundService;
import com.springapp.mvc.Service.ServiceImpl.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
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
        Map<String, BigDecimal> sumStock = stockService.sumByRisk();
        Map<String, BigDecimal> sumLoan = loanService.sumByRisk();
        view.addObject("sumFund", sumFund);
        view.addObject("sumStock", sumStock);
        view.addObject("sumLoan", sumLoan);

        Map<String, BigDecimal> g = new HashMap<String, BigDecimal>();
        BigDecimal amount = BigDecimal.valueOf(0);

        allService.sumByRisk(sumFund, g);
        allService.sumByRisk(sumStock, g);
        allService.sumByRisk(sumLoan, g);
        view.addObject("group", g);

        for(String k : g.keySet()){
            amount = amount.add(g.get(k));
        }
        view.addObject("sum", amount);
        return view;
    }
}
