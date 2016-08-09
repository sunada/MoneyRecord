package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Account;

import java.util.List;

/**
 * Created by Administrator on 2015/9/13.
 */
public interface Dao {
    List<Account> getAccountList(int type);
    boolean save(Account account);
    int delete(int id);
    int update(Account account);
}
