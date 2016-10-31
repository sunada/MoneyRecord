package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.InAllDao;
import com.springapp.mvc.Model.AssetType;
import com.springapp.mvc.Model.Deal;
import com.springapp.mvc.Model.InAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016/4/25.
 */
@Service("allService")
public class InAllService {
    private InAllDao allDao;

    @Autowired
    public void setAllDao(InAllDao allDao){ this.allDao = allDao; }

    public ArrayList<InAll> read(){ return (ArrayList<InAll>)allDao.getAllList();}

    public BigDecimal sum(){ return allDao.sum();}

    public void sumByRisk(Map<String, BigDecimal> map, Map<String, BigDecimal> res){
        Set<String> keys = map.keySet();
        BigDecimal amount = BigDecimal.ZERO;
        for(String k : keys){
            if(res.containsKey(k)){
                amount = res.get(k).add(map.get(k));
            }else{
                amount = map.get(k);
            }
            res.put(k, amount);
        }
        return;
    }

    public void calProfitRate(Date start, Date end, String[] type){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("end", end);
        List<String> arr = Arrays.asList(type);
        List<Deal> deals = new ArrayList<Deal>();
        if (arr.contains(AssetType.FUND.getName())) {
            deals.addAll(allDao.getFundDealList(map));
        }
        if (arr.contains(AssetType.STOCK.getName())) {
            deals.addAll(allDao.getStockDealList(map));
        }

        return;
    }
}
