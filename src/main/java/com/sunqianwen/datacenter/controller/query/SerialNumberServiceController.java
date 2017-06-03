package com.sunqianwen.datacenter.controller.query;

import com.sunqianwen.datacenter.service.SerialNumberService;
import com.sunqianwen.datacenter.util.ResultResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by m.
 */
@Controller
public class SerialNumberServiceController {

    private Logger logger = Logger.getLogger(SerialNumberServiceController.class);

    @Autowired
    private SerialNumberService serialNumberService;

    @RequestMapping(value = "/serial",method = RequestMethod.GET)
    @ResponseBody
    public ResultResponse<String> listDeviceInfos() {
        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setData(serialNumberService.nextOrderID("SO"));

        return resultResponse;
    }
}
