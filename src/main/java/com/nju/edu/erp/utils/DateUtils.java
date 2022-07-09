package com.nju.edu.erp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static public Date transfer_to_date(String str){
        Date date=new Date();
        try {
            date= sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
