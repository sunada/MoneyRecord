package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.BalanceDao;
import com.springapp.mvc.Model.Salary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
@Service
public class BalanceService {
    @Resource
    private BalanceDao balanceDao;

    public List<Salary> getSalaryList(){
        return balanceDao.getSalaryList();
    }

    public int saveSalary(Salary salary){
        return balanceDao.saveSalary(salary);
    }
}
