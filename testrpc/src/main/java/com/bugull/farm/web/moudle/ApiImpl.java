package com.bugull.farm.web.moudle;

import com.bugull.farm.web.Api;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

/**
 * Created by JasonMao on 2017/7/15.
 */
@MotanService(export = "8002" )
public class ApiImpl implements Api {


    public String hello() {

        System.out.println(" note hello ");
        return "xxxxxxxxxxx";
    }
}
