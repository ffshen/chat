package org.pliers.chat.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.pliers.chat.dto.User;
import org.pliers.chat.helper.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author shenx
 * @Description: 消息中心
 * @date 2018/3/20
 */
@Component
public class SimpleEventBus extends AbstractVerticle {

    public static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventBus.class);

    @Override
    public void start() throws Exception {
        EventBus eventBus=vertx.eventBus();
        //设置数据推送出去的时间限制
        DeliveryOptions deliveryOptions=new DeliveryOptions(new JsonObject()
                .put("timeout", 1000000));
        //注册地址,然后对接收到客户端来的消息进行处理
        eventBus.consumer("chat.to.server", message -> {
            JsonObject msgJsonObj = new JsonObject(message.body().toString()) ;
            String loginCode = msgJsonObj.getString("loginCode") ;
            User user = UserHelper.getUser(loginCode) ;
            eventBus.publish("chat.to.client."+msgJsonObj.getString("loginCode"), user.getName()+",服务器收到你的消息" , deliveryOptions);
        });
        //
        eventBus.consumer("api.send.message.to.client", message -> {
            JsonObject msgJsonObj = new JsonObject(message.body().toString()) ;
            String to = msgJsonObj.getString("to") ;
            String address = "chat.to.client" ;
            //发送消息
            if(!StringUtils.isEmpty(to)){
                address = address.concat(".").concat(to) ;
            }
            eventBus.publish(address, msgJsonObj , deliveryOptions);
        });

//        //周期性推送数据
//        vertx.setPeriodic(30000, timerID -> {
//            eventBus.publish("chat.to.client", System.currentTimeMillis() , deliveryOptions);
//            LOGGER.info(new Date()+":推送完毕");
//        });
    }
}
