package com.springapp.mvc.Service;

import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.MyFund;
import com.springapp.mvc.Model.Risk;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/13.
 */
public interface FundService {
    ArrayList<Fund> readFund(boolean valid);
//    ArrayList<Fund> readFund();
    boolean saveFund(Fund fund);
//    boolean saveAip(Fund fund);
    boolean updateAip(Fund fund);
}
