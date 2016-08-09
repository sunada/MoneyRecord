package com.springapp.mvc.Service;

import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.AccountType;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/13.
 */
public interface AccountService {
    //Card getAccountById(Integer accountId);
    boolean saveAccount(Account account);
    ArrayList<Account> readAccount(int type);
    ModelAndView accountDisplay();
    String delAccount(int id);
    String updateAccount(int id, AccountType type, String owner, String accountName, BigDecimal balance);
}
