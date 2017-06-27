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

    public ArrayList<Insurance> display(boolean valid){
        ArrayList<Insurance> res = insuranceDao.display(valid);
        return res;
    }

    public Map<String, Integer> addUpByOwner(ArrayList<Insurance> arr){
        Map<String, Integer> map = new HashMap<String, Integer>();
        String owner;
        Integer tmp = 0;
        Integer sum = 0;
        for(Insurance i : arr){
            owner = i.getOwner();
            tmp = i.getAmount();
            sum += tmp;
            if(map.containsKey(owner)){
                tmp += map.get(owner);
                map.put(owner, tmp);
            }else{
                map.put(owner, tmp);
            }
        }
        map.put("总计", sum);
        return map;
    }
    public boolean save(Insurance insurance){
        return insuranceDao.save(insurance);
    }
}
