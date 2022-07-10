package com.nju.edu.erp.utils;

import java.math.BigDecimal;
import java.util.Arrays;

public class BigDecimalsArrayUtil {


    static public BigDecimal[] getBigDecimals(String level,String discount) {
        BigDecimal[] bigDecimals = new BigDecimal[5];
        for (int i = 0; i < bigDecimals.length; i++) {
            bigDecimals[i] = BigDecimal.ZERO;
        }
        bigDecimals[Integer.parseInt(level) - 1] = new BigDecimal(discount);
        System.out.println("!!!!");
        System.out.println(Arrays.toString(bigDecimals));
        return bigDecimals;
    }
}
