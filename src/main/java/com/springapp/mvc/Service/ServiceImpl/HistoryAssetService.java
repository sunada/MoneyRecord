package com.springapp.mvc.Service.ServiceImpl;

import com.springapp.mvc.Dao.HistoryAssetDao;
import com.springapp.mvc.Model.AssetType;
import com.springapp.mvc.Model.HistoryAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
