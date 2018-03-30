//package org.pliers.chat;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import org.pliers.chat.eventbus.SimpleEventBus;
//import org.pliers.chat.web.SimpleHttpServer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//
//import javax.annotation.PostConstruct;
//
///**
// * @author shenx
// * @Description: 启动Spring
// * @date 2018/3/19
// */
//@SpringBootApplication
//public class AppConfig {
//
//    @Autowired
//    private SimpleHttpServer simpleHttpServer ;
//
//    @Autowired
//    private SimpleEventBus simpleEventBus ;
//
//    @PostConstruct
//    private void deploy(ApplicationReadyEvent event)  {
//        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40)) ;
//        vertx.deployVerticle(simpleHttpServer);
//        vertx.deployVerticle(simpleEventBus);
//    }
//}
