package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.Deposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/11/16.
 */
@Controller
@RequestMapping("deposit")
public class DepositController {
    private static Logger log = LoggerFactory.getLogger(AccountController.class);
    @Resource
    private Deposit deposit;

    //http://localhost:8082/deposit?account=1|manda|招商银行;
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(@RequestParam("account") String account) {
        String[] res = account.split("|");
        log.debug(account);
        return "depositDisplay";
    }


}
