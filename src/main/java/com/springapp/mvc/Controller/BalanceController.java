package com.springapp.mvc.Controller;

import com.springapp.mvc.Service.ServiceImpl.BalanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/10/10.
 */
@Controller
@RequestMapping("income")
public class BalanceController {
    @Resource
    private BalanceService balanceService;

    @RequestMapping
    public ModelAndView balanceSheet(){
        ModelAndView mv = new ModelAndView("balance");
        return mv;
    }

}
