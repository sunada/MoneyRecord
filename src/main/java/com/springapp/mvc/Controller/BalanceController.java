package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.Salary;
import com.springapp.mvc.Service.ServiceImpl.BalanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @RequestMapping
    public ModelAndView balanceSheet(){
        ModelAndView mv = new ModelAndView("balance");
        List<Salary> list = balanceService.getSalaryList();
        mv.addObject("salary", list);

        Map<String, Object> map = balanceService.getExpenseMap();
        mv.addObject("expense", map);
        return mv;
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
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(request.getParameter("date"));
        }catch (Exception e){
            e.printStackTrace();
        }
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

        balanceService.saveSalary(salary);
        return "redirect:/balance";
    }

    @RequestMapping("/saveExpense")
    public String saveExpense(HttpServletRequest request){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(request.getParameter("date"));
        }catch (Exception e){
            e.printStackTrace();
        }
        BigDecimal dailyExpense = new BigDecimal(request.getParameter("dailyExpense"));
        BigDecimal mortgage = new BigDecimal(request.getParameter("mortgage"));
        balanceService.saveExpense(date, dailyExpense, mortgage);
        return "redirect:/balance";
    }

}
