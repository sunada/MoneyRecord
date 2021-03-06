package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Service.ServiceImpl.BalanceService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
@Controller
@RequestMapping("balance")
public class BalanceController {
    @Resource
    private BalanceService balanceService;
    @Resource
    private Salary salary;
    @Resource
    private Income income;
    @Resource
    private Expense expense;
    @Resource
    private Balance balance;
    @Resource
    private SocialFunds socialFunds;

    @RequestMapping
    public ModelAndView balanceSheet(){
        ModelAndView mv = new ModelAndView("balance");
        List<Salary> salaryList = balanceService.getSalaryList();
        mv.addObject("salary", salaryList);

        List<Expense> expenseList = balanceService.getExpenseMap();
        List<Balance> balanceList = balanceService.getBalanceList(salaryList, expenseList);
        mv.addObject("balanceList", balanceList);

        BigDecimal budgetSum = balanceService.getBudgetSum();
        BigDecimal leftSum = balanceService.getLeftSum();
        mv.addObject("budgetSum", budgetSum);
        mv.addObject("leftSum", leftSum);

        String balanceListJson = JSONArray.fromObject(balanceList).toString();
        mv.addObject("balanceListJson", balanceListJson);

        SocialFunds socialFunds = balanceService.getSocialFunds();
        mv.addObject("socialFunds", socialFunds);
        return mv;
    }

    @RequestMapping(value = "/addSocialFunds",method = RequestMethod.POST)
    public String addSocialFunds(HttpServletRequest request){
        BigDecimal hHouseFund = new BigDecimal(request.getParameter("hHouseFund"));
        BigDecimal hMediFund = new BigDecimal(request.getParameter("hMediFund"));
        BigDecimal wHouseFund = new BigDecimal(request.getParameter("wHouseFund"));
        BigDecimal wMediFund = new BigDecimal(request.getParameter("wMediFund"));
        socialFunds.sethHouseFund(hHouseFund);
        socialFunds.sethMediFund(hMediFund);
        socialFunds.setwHouseFund(wHouseFund);
        socialFunds.setwMediFund(wMediFund);
        balanceService.saveSocialFunds(socialFunds);
        return "redirect:/balance";
    }

    @RequestMapping("/addSalary")
    public ModelAndView addSalary(){
        ModelAndView mv = new ModelAndView("salaryAdd");
        return mv;
    }



    @RequestMapping("/addExpense")
    public ModelAndView addExpense(){
        ModelAndView mv = new ModelAndView("expenseAdd");
        return mv;
    }

    @RequestMapping(value="/saveSalary", method= RequestMethod.POST)
    public String saveSalary(HttpServletRequest request){
        String date = request.getParameter("date");
        String owner = request.getParameter("owner");
        BigDecimal fundBase = new BigDecimal(request.getParameter("fundBase"));
        BigDecimal insuranceBase = new BigDecimal(request.getParameter("insuranceBase"));
        BigDecimal beforeTax = new BigDecimal(request.getParameter("beforeTax"));
        BigDecimal afterTax = new BigDecimal(request.getParameter("afterTax"));
        BigDecimal houseFundsCompany = new BigDecimal(request.getParameter("houseFundsCompany"));
        BigDecimal houseFunds = new BigDecimal(request.getParameter("houseFunds"));
        BigDecimal medicare = new BigDecimal(request.getParameter("medicare"));
        BigDecimal medicareCompany = new BigDecimal(request.getParameter("medicareCompany"));
        BigDecimal pensionInsuranceCompany = new BigDecimal(request.getParameter("pensionInsuranceCompany"));
        BigDecimal pensionInsurance = new BigDecimal(request.getParameter("pensionInsurance"));
        BigDecimal unemployInsurance = new BigDecimal(request.getParameter("unemployInsurance"));
        BigDecimal unemployInsuranceCompany = new BigDecimal(request.getParameter("unemployInsuranceCompany"));
        BigDecimal tax = new BigDecimal(request.getParameter("tax"));
        BigDecimal mediaCash = new BigDecimal(request.getParameter("mediaCash"));
        String note = request.getParameter("note");

        salary.setDate(date);
        salary.setOwner(owner);
        salary.setBeforeTax(beforeTax);
        salary.setAfterTax(afterTax);
        salary.setHouseFundsCompany(houseFundsCompany);
        salary.setHouseFunds(houseFunds);
        salary.setFundBase(fundBase);
        salary.setInsuranceBase(insuranceBase);
        salary.setMedicareCompany(medicareCompany);
        salary.setMedicare(medicare);
        salary.setPensionInsuranceCompany(pensionInsuranceCompany);
        salary.setPensionInsurance(pensionInsurance);
        salary.setUnemployInsurance(unemployInsurance);
        salary.setUnemployInsuranceCompany(unemployInsuranceCompany);
        salary.setTax(tax);
        salary.setMediaCash(mediaCash);
        salary.setNote(note);

        balanceService.saveSalary(salary);
        balanceService.updateBalance(date, salary);

        socialFunds = balanceService.getSocialFunds();
        if(owner.equals("老婆")){
            if(socialFunds != null){
//                socialFunds.setwMediFund(socialFunds.getwMediFund().add(mediaCash));
                socialFunds.setwHouseFund(socialFunds.getwHouseFund().add(houseFunds).add(houseFundsCompany));
            }else{
                socialFunds.setwHouseFund(houseFunds.add(houseFundsCompany));
//                socialFunds.setwMediFund(mediaCash);
            }
        }else{
            if(socialFunds != null){
                socialFunds.sethMediFund(socialFunds.gethMediFund().add(mediaCash));
                socialFunds.sethHouseFund(socialFunds.gethHouseFund().add(houseFunds).add(houseFundsCompany));
            }else{
                socialFunds.sethHouseFund(houseFunds.add(houseFundsCompany));
                socialFunds.sethMediFund(mediaCash);
            }
        }
        balanceService.saveSocialFunds(socialFunds);
        return "redirect:/balance";
    }

    @RequestMapping("/deleteSalary")
    public String deleteSalary(HttpServletRequest request){
        int id = Integer.valueOf(request.getParameter("id"));
        Salary salary = balanceService.getSalaryById(id);

        salary.setAfterTax(BigDecimal.ZERO.subtract(salary.getAfterTax()));
        salary.setHouseFundsCompany(BigDecimal.ZERO.subtract(salary.getHouseFundsCompany()));
        salary.setHouseFunds(BigDecimal.ZERO.subtract(salary.getHouseFunds()));
        salary.setMediaCash(BigDecimal.ZERO.subtract(salary.getMediaCash()));

        balanceService.updateBalance(salary.getDate(), salary);
        socialFunds = balanceService.getSocialFunds();
        if(salary.getOwner() == "wife"){
            socialFunds.setwHouseFund(socialFunds.getwHouseFund().add(salary.getHouseFunds()));
        }else{
            socialFunds.sethMediFund(socialFunds.gethMediFund().add(salary.getMediaCash()));
            socialFunds.sethHouseFund(socialFunds.gethHouseFund().add(salary.getHouseFunds()));
        }
        balanceService.saveSocialFunds(socialFunds);
        balanceService.deleteSalary(id);
        return "redirect:/balance";
    }

    @RequestMapping("/saveExpense")
    public String saveExpense(HttpServletRequest request){
        String date = request.getParameter("date");
        BigDecimal dailyExpense = new BigDecimal(request.getParameter("dailyExpense"));
        BigDecimal mortgage = new BigDecimal(request.getParameter("mortgage"));
        String note = request.getParameter("note");
        expense.setDailyExpense(BigDecimal.ZERO.subtract(dailyExpense));
        expense.setDate(date);
        expense.setMortgage(BigDecimal.ZERO.subtract(mortgage));
        expense.setNote(note);
        balanceService.saveExpense(expense);
        balanceService.updateBalance(date, expense);
        return "redirect:/balance";
    }



}
