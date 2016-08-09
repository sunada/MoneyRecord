package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.FundDao;
import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.MyFund;
import com.springapp.mvc.Model.Risk;
import com.springapp.mvc.Service.FundService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/13.
 */
@Service("fundService")
public class FundServiceImpl implements FundService {
    @Resource
    private FundDao fundDao;

    @Override
    public ArrayList<Fund> readFund(boolean valid){
        ArrayList<Fund> funds = (ArrayList)fundDao.getFundList(valid);
        return funds;
    }

    @Override
    public boolean saveFund(Fund fund){
        return fundDao.save(fund);
    }

    @Override
    public boolean updateAip(Fund fund) { return fundDao.updateAip(fund);}


}
