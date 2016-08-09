package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.AccountDao;
import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.AccountType;
import com.springapp.mvc.Service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/13.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService{

    @Resource
    private AccountDao accountDao;
    @Resource
    private Account account;

    @Override
    public boolean saveAccount(Account account){
        return accountDao.save(account);
        //return true;
    }

    @Override
    public ArrayList<Account> readAccount(int type){
        return (ArrayList)accountDao.getAccountList(type);
    }

    public ModelAndView accountDisplay(){
        ModelAndView view = new ModelAndView("accountDisplay");
        ArrayList<Account> accounts = null;

        accounts = readAccount(AccountType.CREDIT.getIndex());
        view.addObject("creditCards", accounts);

        accounts = readAccount(AccountType.DEPOSIT.getIndex());
        view.addObject("debitCards", accounts);

        accounts = readAccount(AccountType.FUND.getIndex());
        view.addObject("funds", accounts);

        accounts = readAccount(AccountType.SECURITIES.getIndex());
        view.addObject("As", accounts);

        accounts = readAccount(AccountType.LOAN.getIndex());
        view.addObject("loans", accounts);

        return view;
    }

    public String delAccount(int id){
        accountDao.delete(id);
        return "redirect:/";
    }

    public String updateAccount(int id, AccountType type, String owner, String accountName, BigDecimal balance){
        account.setAccountId(id);
        account.setOwner(owner);
        account.setAccount(accountName);
        account.setBalance(balance);
        account.setType(type);
        accountDao.update(account);
        return "redirect:/";
    }

}
