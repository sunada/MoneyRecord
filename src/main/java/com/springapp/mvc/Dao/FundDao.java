package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.MyFund;
import com.springapp.mvc.Model.Risk;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
@Repository("fundDao")
public class FundDao{
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<Fund> getFundList(boolean valid){
        List<Fund> fundList = new ArrayList<Fund>();
        try{
            fundList = sqlSession.selectList("Funds.getFundListByValid", valid);
//            log.debug("in FundDao: "  + fundList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return fundList;
    }

    public List<Fund> getFundList(){
        List<Fund> fundList = new ArrayList<Fund>();
        try{
            fundList = sqlSession.selectList("Funds.getFundList");
//            log.debug("in FundDao: "  + fundList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return fundList;
    }

    public boolean save(Fund fund){
        int res = -1;
        try{
            res = sqlSession.insert("Funds.insertFund", fund);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean updateAip(Fund fund){
        int res = -1;
        try{
            res = sqlSession.update("Funds.updateAip", fund);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }
}
