package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Service.ServiceImpl.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/24.
 */
@Controller
@RequestMapping("/loan")
public class LoanController {
    private static Logger log = LoggerFactory.getLogger(FundController.class);

    private LoanService loanService;
    private Loan loan;

    @Autowired
    public void setLoanService(LoanService loanService){ this.loanService = loanService;}

    @Autowired
    public void setLoan(Loan loan){ this.loan = loan;}

    @RequestMapping
    public ModelAndView loanDisplay(){
        ModelAndView view = new ModelAndView("loanDisplay");
        ArrayList<Loan> loanValid = loanService.read(true);
        view.addObject("loanValid", loanValid);
        ArrayList<Loan> loanNotValid = loanService.read(false);
        view.addObject("loanNotValid", loanNotValid);

        Map<String, BigDecimal> map = loanService.sumByRisk();
        view.addObject("group", map);

        HashMap<String, BigDecimal> sum = loanService.sum();
        view.addObject("sum", sum);

        Map<String, BigDecimal> sumByBelongTo = loanService.sumByBelongTo();
        view.addObject("sumByBelongTo", sumByBelongTo);

        BigDecimal amountAll = BigDecimal.ZERO;
        if(sum != null){
            amountAll = sum.get("waitPIAll");
        }
        view.addObject("amountAll", amountAll);

        return view;
    }

    @RequestMapping("loanAdd")
    public String loanAdd(){ return "loanAdd";}

    @RequestMapping(value = "loanSave", method = RequestMethod.POST)
    public String loanSave(HttpServletRequest request){
        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        BigDecimal interestRate = new BigDecimal(request.getParameter("interestRate"));

        loan.setCode(code);
        loan.setBelongTo(belongTo);
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
//        loan.setInter(LoanInterval.valueOf(request.getParameter("inter")));
//        loan.setInterLong(new BigDecimal(request.getParameter("interLong")));
        loan.setApproach(PayBack.valueOf(request.getParameter("approach")));
        loan.setRisk(Risk.valueOf(request.getParameter("risk")));

        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date time = sdf.parse(request.getParameter("endTime"));

            loan.setEndTime(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            long time1 = cal.getTimeInMillis();
            time = sdf.parse(request.getParameter("startTime"));
            loan.setStartTime(time);
            cal = Calendar.getInstance();
            cal.setTime(time);
            long time2 = cal.getTimeInMillis();
            BigDecimal interest = new BigDecimal(0.00);
            if(!loan.getApproach().equals("epi")){  //等额本息
                interest = calInterest(time1, time2, loan.getInterestRate(), loan.getAmount());
            }
            loan.setInterest(interest);
            loan.setHadPI(BigDecimal.ZERO);
        }catch (Exception e){
            e.printStackTrace();
        }

        log.debug("In LoansController.save, {}", loan.toString());
        if (loanService.saveLoan(loan)) {
            return "redirect:/loan";
        }else{
            return "redirect:/";
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        String code = request.getParameter("code");
        String belongTo = request.getParameter("belongTo");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        BigDecimal interestRate = new BigDecimal(request.getParameter("interestRate"));
        BigDecimal interest = new BigDecimal(request.getParameter("interest"));

        loan.setCode(code);
        loan.setBelongTo(belongTo);
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
//        loan.setInter(LoanInterval.valueOf(request.getParameter("inter")));
//        loan.setInterLong(new BigDecimal(request.getParameter("interLong")));
        loan.setApproach(PayBack.valueOf(request.getParameter("approach")));
        loan.setRisk(Risk.valueOf(request.getParameter("risk")));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        try{
            BigDecimal hadPI = BigDecimal.ZERO;
            hadPI = new BigDecimal(request.getParameter("hadPI"));
            BigDecimal waitPI = BigDecimal.ZERO;
            waitPI = new BigDecimal(request.getParameter("waitPI"));
            loan.setHadPI(hadPI);
            loan.setWaitPI(waitPI);
            loan.setStartTime(sdf.parse(request.getParameter("startTime")));
//            loan.setRealInterestRate(new BigDecimal(request.getParameter("realInterestRate")));
//            loan.setValid(Integer.valueOf(request.getParameter("valid")));

            Date time = sdf.parse(request.getParameter("endTime"));
            loan.setEndTime(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            long time1 = cal.getTimeInMillis();
            time = sdf.parse(request.getParameter("startTime"));
            loan.setStartTime(time);
            cal = Calendar.getInstance();
            cal.setTime(time);
            long time2 = cal.getTimeInMillis();
//            BigDecimal interest = new BigDecimal(0.00);
//            if(!loan.getApproach().equals("等额本息")){
//                interest = calInterest(time1, time2, loan.getInterestRate(), loan.getAmount());
//            }
            loan.setInterest(interest);
//            loan.setInterest(new BigDecimal(request.getParameter("interest")));
        }catch (Exception e){
            e.printStackTrace();
        }

        log.debug("In LoansController.update, {}", loan.toString());
        if (loanService.update(loan)) {
            return "redirect:/loan";
        }else{
            return "redirect:/";
        }
    }

    public BigDecimal calInterest(long time1, long time2, BigDecimal ir, BigDecimal amount){
        BigDecimal days = new BigDecimal((time1 - time2) / (1000 * 3600 * 24));
        BigDecimal interest = amount.multiply(ir).multiply(days).divide(new BigDecimal(100));
        interest = interest.divide(new BigDecimal(360), 2, RoundingMode.HALF_UP);
        return interest;
    }
}
