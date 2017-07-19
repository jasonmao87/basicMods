package com.bugull.farm.web.init;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.*;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by JasonMao on 2017/7/6.
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages={"com.bugull.farm.web"})
public class AppInit extends SpringBootServletInitializer{



    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //BuguMongoApplication.getInstance().start();
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        return application.sources(AppInit.class);
    }
    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.bugull.farm.web");
        System.out.println("init motan AnnotationBean");
        return motanAnnotationBean;
    }



    @Bean(name = "motanProtocol")
    public ProtocolConfigBean protocolConfig1() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setLoadbalance("random");
        config.setMaxContentLength(1048576);
        System.out.println("哦配置 ProtocolConfigBean ");
        return config;
    }

    @Bean(name = "zookeeper")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        // 本地配置
        /*
        config.setRegProtocol("local");
        */
        // zookeeper 配置
        config.setRegProtocol("zookeeper");
        //config.setAddress("121.40.115.131:2181,120.26.109.144:2181");
        config.setAddress("127.0.0.1:2181");
        //config.setPort(2181);
        config.setName("zookeeper");
        System.out.println("配置 zookeeper");
        return config;
    }


    @Bean(name = "motantestClientBasicConfig")
    public BasicRefererConfigBean baseRefererConfig() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motanProtocol");
        config.setGroup("default_rpc");
        config.setModule("testRPC");
        config.setApplication("testRPC");
        config.setRegistry("zookeeper");
        config.setCheck(false);
        config.setAccessLog(true);
        config.setRetries(2);
        config.setThrowException(true);
        System.out.println("配置 客户端 ");

        return config;
    }






}
