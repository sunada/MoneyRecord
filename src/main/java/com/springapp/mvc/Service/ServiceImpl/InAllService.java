package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.InAllDao;
import com.springapp.mvc.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    public void picture(MonthAsset monthAsset){
        if(allDao.getPicture(monthAsset.getMonth())) {
            allDao.updatePicture(monthAsset);
        }else{
            allDao.savePicture(monthAsset);
        }
    }

    public List<MonthAsset> getMonthAssets(){
        return allDao.getMonthAssets();
    }

    public List<MonthAssetLeft> calMonthAssetLeft(List<MonthAsset> monthAsset, List<Balance> monthLeft){
        List<MonthAssetLeft> list = new ArrayList<MonthAssetLeft>();
        if(monthAsset.size() == 0) return null;
        BigDecimal amount,usdAmount,hkdAmount,others;
        int monthAssetIndex = 0;
        int monthLeftIndex = 0;
        for(int i = 0; i < monthAsset.size(); i++){
            MonthAssetLeft mal = new MonthAssetLeft();
            try{
                while(true) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                    Date assetMonth = formatter.parse(monthAsset.get(monthAssetIndex).getMonth());
                    Date leftMonth = formatter.parse(monthLeft.get(monthLeftIndex).getDate());
                    if(assetMonth.after(leftMonth)) monthAssetIndex++;
                    else if(assetMonth.before(leftMonth)) monthLeftIndex++;
                    else{
                        monthAssetIndex++;
                        monthLeftIndex++;
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            mal.setMonth(monthAsset.get(i).getMonth());
            mal.setLeft(monthLeft.get(i).getLeft());
            mal.setMonthAsset(monthAsset.get(i));
            if(i < monthAsset.size() - 1){
                MonthAsset tmp = monthAsset.get(i + 1);
                amount = tmp.getAmount();
                usdAmount = tmp.getUsd();
                hkdAmount = tmp.getHkd();
                others = tmp.getFunds().add(tmp.getStocks()).add(tmp.getP2p()).add(tmp.getSocialInsurance());
            }else{
                amount = BigDecimal.ZERO;
                usdAmount = amount;
                hkdAmount = amount;
                others = amount;
            }
            MonthAsset tmp = monthAsset.get(i);
            mal.setAmountIncrease(tmp.getAmount().subtract(amount));
            mal.setUsdIncrease(tmp.getUsd().subtract(usdAmount));
            mal.setHkdIncrease(tmp.getHkd().subtract(hkdAmount));
            mal.setOtherIncrease(tmp.getSocialInsurance().add(tmp.getP2p()).add(tmp.getStocks()).add(tmp.getFunds()).subtract(others));
            mal.setOtherIncome(mal.getAmountIncrease().subtract(mal.getLeft()));

            list.add(mal);
        }
        return list;
    }
}
