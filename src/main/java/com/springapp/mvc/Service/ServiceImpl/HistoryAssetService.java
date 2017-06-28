package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.HistoryAssetDao;
import com.springapp.mvc.Model.AssetType;
import com.springapp.mvc.Model.HistoryAsset;
import com.springapp.mvc.Model.MyFund;
import com.springapp.mvc.Model.Risk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/5.
 */
@Service("historyAssetService")
public class HistoryAssetService {
    private HistoryAssetDao historyAssetDao;
    private HistoryAsset historyAsset;

    @Autowired
    public void setHistoryAssetDao(HistoryAssetDao historyAssetDao){ this.historyAssetDao = historyAssetDao;}
    @Autowired
    public void setHistoryAsset(HistoryAsset historyAsset){ this.historyAsset = historyAsset;}

    public List<HistoryAsset> readHistory(AssetType type){
        return historyAssetDao.getHistoryAssetList(type);
    }

    public Map<String, List<BigDecimal>> getHistoryProfit(AssetType type){
        List<HistoryAsset> assets = readHistory(type);
        Map<String, List<BigDecimal>> res = new HashMap<String, List<BigDecimal>>();  //key: 风险
        List<BigDecimal> list;  //0:成本 1：盈利 3:收益率
        BigDecimal costInAll = BigDecimal.ZERO;
        BigDecimal profitInAll = BigDecimal.ZERO;
        for(HistoryAsset asset : assets){
            list = new ArrayList<BigDecimal>();
            String risk = "";
            if(asset.getRisk() != null) {
                risk = asset.getRisk().getName();
            }else{
                risk = Risk.HIGH.getName();
            }

            if(res.containsKey(risk)){
                List<BigDecimal> newList = res.get(risk);
//                list.add(newList.get(0).add(asset.getCost()));
                list.add(newList.get(0).add(asset.getProfit()));
            }else{
//                list.add(asset.getCost());
                list.add(asset.getProfit());
            }
//            costInAll = costInAll.add(asset.getCost());
            profitInAll = profitInAll.add(asset.getProfit());
//            list.add(list.get(1).divide(list.get(0), 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
            res.put(risk, list);
        }
        list = new ArrayList<BigDecimal>();
//        list.add(costInAll);
        list.add(profitInAll);
//        list.add(profitInAll.divide(costInAll, 2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
        res.put("总计", list);
        return res;
    }

}
