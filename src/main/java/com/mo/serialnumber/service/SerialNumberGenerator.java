package com.mo.serialnumber.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单号生成
 * @author MoXingwang on 2017-11-17.
 */
public class SerialNumberGenerator {
    private static final int USER_ID_LENGTH = 4;

    /**
     * 订单号生成
     * @param userId
     * @return
     */
    public static String generate(String userId){
        if(StringUtils.isBlank(userId)){
            userId = ThreadLocalRandom.current().nextLong((int)Math.pow(10, USER_ID_LENGTH - 1), (int)Math.pow(10, USER_ID_LENGTH) - 1) + "";
        }else if(userId.length() < USER_ID_LENGTH){
            userId = StringUtils.leftPad(userId,USER_ID_LENGTH,"0");
        }else if(userId.length() > USER_ID_LENGTH){
            userId = userId.substring(userId.length() - USER_ID_LENGTH,userId.length());
        }

        Calendar can = Calendar.getInstance();
        int year = can.get(Calendar.YEAR) - 2017;
        int days = can.get(Calendar.DAY_OF_YEAR);
        int hour = can.get(Calendar.HOUR_OF_DAY);
        int min = can.get(Calendar.MINUTE);

        int minutes = (hour - 8 < 0 ? 0 : hour - 8) * 60 + min;

        String req = (year * 366 + days) + StringUtils.leftPad(minutes + "",3,"0");

        req +=  StringUtils.leftPad(ThreadLocalRandom.current().nextLong(0, 999999) + "",6,"0");

        return req + userId;
    }

    /**
     * 订单号生成
     * @param prefix
     * @param userId
     * @return
     */
    public static String generate(String prefix,String userId){
        return prefix + generate(userId);
    }


    /**
     * 推荐不再使用前缀
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(generate("11493677"));
        System.out.println(generate("11493677"));
        System.out.println(generate("11493677"));
        System.out.println(generate("11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","11493677"));
        System.out.println(generate("SO","1111149300677"));
        System.out.println(generate("SO","677"));
        System.out.println(generate("SO",""));
        System.out.println(generate("SO"," "));
        System.out.println(generate("SO","   "));
        System.out.println(generate("SO",null));
    }

}
