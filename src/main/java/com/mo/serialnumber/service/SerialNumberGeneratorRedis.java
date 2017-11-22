package com.mo.serialnumber.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 订单号生成
 * @author MoXingwang on 2017-11-17.
 */
@Service
public class SerialNumberGeneratorRedis {
    private static final int USER_ID_LENGTH = 4;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final String ORDER_SERIAL_KEY = "trade:order:serial:";

    /**
     * 订单号生成
     * @param userId
     * @return
     */
    public String generate(String userId){
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

        try{
            String redisKey = ORDER_SERIAL_KEY + days + hour;
            long cacheCount = redisTemplate.opsForValue().increment(redisKey, 1);
            redisTemplate.expire(redisKey, 1, TimeUnit.HOURS);
            if(cacheCount>999999){
                redisTemplate.delete(redisKey);
                String tempCount = cacheCount+"";
                req += tempCount.substring(tempCount.length()-6,tempCount.length());
            }else {
                req +=  StringUtils.leftPad(cacheCount + "",6,"0");
            }
        }catch (Exception e){
            req +=  StringUtils.leftPad(ThreadLocalRandom.current().nextLong(0, 999999) + "",6,"0");
        }

        return req + userId;
    }

    /**
     * 订单号生成
     * @param prefix
     * @param userId
     * @return
     */
    public String generate(String prefix,String userId){
        return prefix + generate(userId);
    }

}
