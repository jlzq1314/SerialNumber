package com.mo.serialnumber.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author MoXingwang on 2017-11-17.
 */
public class SerialNumberGenerator {
    private static final int USER_ID_LENGTH = 5;

    public static String generate(String prefix,String userId){
        if(StringUtils.isBlank(userId)){
            userId = ThreadLocalRandom.current().nextLong(10000, 99999) + "";
        }else if(userId.length() < USER_ID_LENGTH){
            userId = addZeroForNum(userId,USER_ID_LENGTH);
        }else if(userId.length() > USER_ID_LENGTH){
            userId = userId.substring(userId.length() - USER_ID_LENGTH,userId.length());
        }

        Calendar can = Calendar.getInstance();
        int year = can.get(Calendar.YEAR) - 2017;
        int month = can.get(Calendar.MONTH) + 1;
        int days = can.get(Calendar.DAY_OF_YEAR);

        String req = (year * 12 + month) + "" + (days + ThreadLocalRandom.current().nextLong(370, 630));

        long random = ThreadLocalRandom.current().nextLong(0, 999999) ;
        req +=  addZeroForNum(random + "",6);

        if(null != prefix){
            return prefix + req + userId;
        }
        return req + userId;
    }

    private static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(str).append("0");
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11111493677"));
        System.out.println(generate("SO","677"));
        System.out.println(generate("SO",""));
        System.out.println(generate("SO"," "));
        System.out.println(generate("SO","   "));
        System.out.println(generate("SO",null));
    }

}
