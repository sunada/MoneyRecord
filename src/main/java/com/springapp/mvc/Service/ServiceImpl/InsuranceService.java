package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.InsuranceDao;
import com.springapp.mvc.Model.Insurance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/25.
 */
@Service("insuranceService")
public class InsuranceService {

    @Resource
    public InsuranceDao insuranceDao;

    public ArrayList<Insurance> display(){
        ArrayList<Insurance> res = insuranceDao.display();
        return res;
    }

    public Map<String, Integer> addUpByOwner(ArrayList<Insurance> arr){
        Map<String, Integer> map = new HashMap<String, Integer>();
        String owner;
        Integer sum = 0;
        for(Insurance i : arr){
            owner = i.getBelongTo();
            sum = i.getAmount();
            if(map.containsKey(owner)){
                sum += map.get(owner);
                map.put(owner, sum);
            }else{
                map.put(owner, sum);
            }
        }
        return map;
    }
    public boolean save(Insurance insurance){
        return insuranceDao.save(insurance);
    }
}
