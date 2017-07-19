package com.bugull.farm.web;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by JasonMao on 2017/7/19.
 */
@Controller
@RequestMapping("/hello")
public class Hello {
    @MotanReferer(basicReferer = "motantestClientBasicConfig" ,registry = "zookeeper")
    Api api ;
    @RequestMapping("")
    public String hello(){
        System.out.println(api.hello());
        return  "hello";

    }

}
