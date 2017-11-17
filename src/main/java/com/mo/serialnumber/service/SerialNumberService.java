package com.mo.serialnumber.service;

import com.mo.serialnumber.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;

/**
 * 序列号生成器
 * Created by MoXingwang on 2016-07-16 17:18.
 */
@Service
public class SerialNumberService {

    private static final Logger logger = LoggerFactory.getLogger(SerialNumberService.class);

    @Autowired
    private OrderMapper orderMapper;


    private int serverID = 0;
    private long currOrderSequence = 0;
    private long currTimestamp = 0;


    public String nextOrderID2(String prefix) {
        String result = SerialNumberGenerator.generate(prefix,"657588");

        try {
            orderMapper.insert(result);
        }catch (Exception e){
            logger.error("插入异常{}",result,e);
        }

        return result;
    }

    /**
     * 初始化机器码
     */
    @PostConstruct
    public void init() {
        serverID = 0;

    }


    public synchronized String nextOrderID(String prefix) {

        Calendar can = Calendar.getInstance();
        int year = can.get(Calendar.YEAR) - 2017;
        int month = can.get(Calendar.MONTH) + 1;
        int day = can.get(Calendar.DAY_OF_MONTH);
        int hour = can.get(Calendar.HOUR_OF_DAY);
        int min = can.get(Calendar.MINUTE);
        int sec = can.get(Calendar.SECOND);
        int ms = can.get(Calendar.MILLISECOND);

        long req = year;
        req = req << 4 | month;
        req = req << 5 | day;
        req = req << 5 | hour;
        req = req << 6 | min;
        req = req << 6 | sec;
        req = req << 10 | ms;

        //豪秒变化后 ， 回归currOrderSequence=0
        if (req > currTimestamp) {
            currOrderSequence = 0;
            currTimestamp = req;
        } else {
            currOrderSequence++;
        }


        req = req << 7 | serverID;
        req = req << 18 | currOrderSequence;

        String s = prefix + req;

        try {
            orderMapper.insert(s);
        } catch (Exception e) {
            logger.error("error--,", e);
        }

        return s;
    }
}