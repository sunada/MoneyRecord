package com.springapp.mvc.Util;

import com.springapp.mvc.Model.Risk;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/16.
 */
public interface ConstantInterface {
    BigDecimal Budget = new BigDecimal(7000);

    BigDecimal LOW = new BigDecimal(0.20);
    BigDecimal MID = new BigDecimal(0.45);
    BigDecimal HIGH = new BigDecimal(0.35);

}