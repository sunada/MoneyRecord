package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.AccountType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/30.
 */
@Repository
public class AccountDao implements Dao{
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<Account> getAccountList(int type){
        List<Account> accList = new ArrayList<Account>();
        try{
            accList = sqlSession.selectList("Accounts.getAccountList", type);
            log.debug("in AccountDao: "  + accList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return accList;
    }

    public boolean save(Account account){
        int res = -1;
        try{
            res = sqlSession.insert("Accounts.insertAccount", account);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public int delete(int id){
        int res = -1;
        try{
            res = sqlSession.delete("Accounts.deleteAccount", id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public int update(Account account){
        int res = -1;
            try{
            res = sqlSession.update("Accounts.updateAccount", account);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}

