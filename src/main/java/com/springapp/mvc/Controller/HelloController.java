package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.*;
import com.springapp.mvc.Service.AccountService;
import com.springapp.mvc.Service.FundService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping
    public ModelAndView hello(){
        ModelAndView view = new ModelAndView("hello");
        return  view;
    }

    @RequestMapping("/test")
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView("test");
        return mv;
    }

}