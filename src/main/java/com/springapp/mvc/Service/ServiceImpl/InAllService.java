package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.InAllDao;
import com.springapp.mvc.Model.InAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
}
