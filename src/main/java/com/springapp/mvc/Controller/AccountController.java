package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.AccountType;
import com.springapp.mvc.Service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;


@Controller
@RequestMapping("/account")
public class AccountController {
    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    private Account account;

    @Autowired
    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }

    @Autowired
    public void setAccount(Account account) {this.account = account;}

    @RequestMapping
    public ModelAndView accountDisplay(){
        return accountService.accountDisplay();
    }

    @RequestMapping(value = "/accountAdd", method = RequestMethod.GET)
    public String printWelcome() {
        return "accountAdd";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("type") String typeName, @RequestParam("account") String accountName,
                             @RequestParam("owner") String owner){
        AccountType accountType = AccountType.valueOf(typeName);
        BigDecimal balance = new BigDecimal("0.00");
        account.setAccount(accountName);
        account.setType(accountType);
        account.setOwner(owner);
        account.setBalance(balance);
        log.debug("In AccountsController.save, {}", account.toString());
        if (accountService.saveAccount(account)) {
//            return accountService.accountDisplay();
            return "redirect:/";
        }else{
            return "redirect:/hello";
        }
    }

    @RequestMapping("/delete")
    public String delAccount(@RequestParam("id") int id){
        return accountService.delAccount(id);
    }

    @RequestMapping("/update")
    public String editAccount(@RequestParam("id") int id, @RequestParam("type") String type,
                              @RequestParam("owner") String owner,@RequestParam("account")String accountName,
                              @RequestParam("balance")String balance){
        return accountService.updateAccount(id, AccountType.valueOf(type), owner, accountName, new BigDecimal(balance));
    }
}