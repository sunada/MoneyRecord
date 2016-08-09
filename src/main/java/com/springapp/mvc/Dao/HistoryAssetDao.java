package com.springapp.mvc.Dao;

import com.springapp.mvc.Model.AssetType;
import com.springapp.mvc.Model.HistoryAsset;
import com.springapp.mvc.Model.Loan;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/10.
 */
@Repository("historyAssetDao")
public class HistoryAssetDao {
    private static Logger log = LoggerFactory.getLogger(AccountDao.class);

    @Resource
    private SqlSession sqlSession;

    public List<HistoryAsset> getHistoryAssetList(AssetType type){
        List<HistoryAsset> historyAssetList = new ArrayList<HistoryAsset>();
        try{
            historyAssetList = sqlSession.selectList("HistoryAssets.getList", type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return historyAssetList;
    }

    public boolean save(HistoryAsset historyAsset){
        int res = -1;
        try{
            res = sqlSession.insert("HistoryAssets.insertHistoryAsset", historyAsset);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }

    public boolean update(HistoryAsset historyAsset){
        int res = -1;
        try{
            res = sqlSession.update("HistoryAssets.update", historyAsset);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res != -1;
    }
}
