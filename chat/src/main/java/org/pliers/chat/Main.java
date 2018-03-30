package org.pliers.chat;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.pliers.chat.eventbus.SimpleEventBus;
import org.pliers.chat.web.SimpleHttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenx
 * @Description: 聊天服务
 * @date 2018/3/19
 */
@SpringBootApplication
public class Main {

    @Autowired
    private SimpleHttpServer simpleHttpServer ;

    @Autowired
    private SimpleEventBus simpleEventBus ;

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Main.class);

    }

    @PostConstruct
    public void deployVerticle() {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40)) ;
        vertx.deployVerticle(simpleHttpServer);
        vertx.deployVerticle(simpleEventBus);
    }



}
