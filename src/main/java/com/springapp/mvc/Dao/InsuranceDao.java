package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.Insurance;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/25.
 */
@Repository("insuranceDao")
public class InsuranceDao {

    @Resource
    private SqlSession sqlSession;

    public ArrayList<Insurance> display(){
        return (ArrayList)sqlSession.selectList("Insurance.display");
    }

    public boolean save(Insurance insurance){
        sqlSession.insert("Insurance.insert", insurance);
        return true;
    }
}

