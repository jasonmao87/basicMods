package com.bugull.farm.web.init;

import com.bugull.farm.web.filter.MotanExceptionFilter;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.AnnotationBean;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import com.weibo.api.motan.config.springsupport.ProtocolConfigBean;
import com.weibo.api.motan.config.springsupport.RegistryConfigBean;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Created by JasonMao on 2017/7/15.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        ConfigurableApplicationContext context =  SpringApplication.run(App.class, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("server start...");
    }



    /**
     * 1、声明Annotation用来指定需要解析的包名
     * @return
     */
    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.bugull.farm.web");
        return motanAnnotationBean;
    }

    /**
     * 配置 ProtocolConfig
     * @return
     */
    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig1() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setMaxContentLength(1048576);

        config.setRequestTimeout( 1000 );

        config.setFilter("com.bugull.farm.web.filter.MotanExceptionFilter");

        return config;
    }

    /**
     * 配置 RegistryConfig
     * @return
     */
    @Bean(name = "registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        // 本地配置
        /*
        config.setRegProtocol("local");
        */
        // zookeeper 配置
        config.setRegProtocol("zookeeper");
        config.setAddress("121.40.115.131:2181,120.26.109.144:2181");
        //config.setAddress("127.0.0.1:2181");
        //config.setPort(2181);
        config.setName("zookeeper");
        return config;
    }

    /**
     * 配置 BasicServiceConfig
     * @return
     */
    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("8002");
        config.setGroup("testRPC");
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setModule("testRPC");
        config.setApplication("testRPC");
        config.setRegistry("registry");
        config.setThrowException(false);
        return config;
    }

}
