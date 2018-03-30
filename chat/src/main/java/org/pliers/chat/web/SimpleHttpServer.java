package org.pliers.chat.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import org.pliers.chat.Prop;
import org.pliers.chat.dto.MyMessage;
import org.pliers.chat.dto.User;
import org.pliers.chat.enumclass.MessageEventType;
import org.pliers.chat.helper.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * @author shenx
 * @Description: Web服务
 * @date 2018/3/19
 */
@Component
public class SimpleHttpServer extends AbstractVerticle {

    @Autowired
    private Prop prop ;

    public static final Logger LOGGER = LoggerFactory.getLogger(SimpleHttpServer.class);

    @Override
    public void start() throws Exception {
        //Router配置
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create().setBodyLimit(-1));
        ///CORS
        /*
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");
        allowedHeaders.add("Authorization");
        ;

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);

        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);

        router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
        */
        //WebSocket
        SockJSHandlerOptions sockjsopt  =   new SockJSHandlerOptions()
                .setHeartbeatInterval(10000);//默认25秒，具体查看SockJSHandlerOptions类
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx,sockjsopt);
        BridgeOptions options = new BridgeOptions();
        options.addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"));
        PermittedOptions outboundPermitted1 = new PermittedOptions().setAddress("chat.to.client");
        PermittedOptions outboundPermitted2 = new PermittedOptions().setAddressRegex("chat.to.client\\..+");
        options.addOutboundPermitted(outboundPermitted1).addOutboundPermitted(outboundPermitted2);
        sockJSHandler.bridge(options);
        router.route("/eventbus/*").handler(sockJSHandler);
        /////JWT
        JsonObject authConfig = new JsonObject()
                .put("keyStore", new JsonObject()
                        .put("type", prop.keystoreType)
                        .put("path", prop.keystorePath)
                        .put("password", prop.keystorePassword)) ;
        JWTAuth  authProvider = JWTAuth.create(vertx,new JWTAuthOptions(authConfig));
        ///JWT校验的服务

        router.route("/chat/api/*").handler(JWTAuthHandler.create(authProvider)) ;

        router.get("/chat/api/getLoginUserList").handler(ctx -> {
            String name = ctx.user().principal().getString("sub") ;
            JsonArray jsonArray = new JsonArray(UserHelper.SINGLE.getLoginUser() );
            JsonObject returnJson = new JsonObject()
                    .put("code",RestResult.CD1[0])
                    .put("desc",RestResult.CD1[1])
                    .put("data",jsonArray)
            ;
            ctx.response().end(returnJson.encode()) ;
        });

        router.put("/chat/api/sendMessage").handler(ctx -> {
            JsonObject bodyJsonObject = ctx.getBodyAsJson() ;
            MyMessage msg = new MyMessage(bodyJsonObject) ;
            msg.getToList().stream().forEach(to->{
                User user = UserHelper.SINGLE.getUser(msg.getFrom()) ;
                bodyJsonObject.put("to",to);
                bodyJsonObject.put("from",user.getName()) ;
                vertx.eventBus().send("api.send.message.to.client",bodyJsonObject);
            });
            JsonObject returnJson = new JsonObject()
                    .put("code",RestResult.CD1[0])
                    .put("desc",RestResult.CD1[1]);
            ctx.response().end(returnJson.encode()) ;
        });

        ///Web服务
        router.route().handler(BodyHandler.create());
        router.post("/chat/login").handler(ctx -> {
            JsonObject bodyJsonObject = ctx.getBodyAsJson() ;
            String loginCode = bodyJsonObject.getString("loginCode") ;
            User user = UserHelper.SINGLE.getUser(loginCode) ;
            if(!Objects.isNull(user)){
                JsonObject payload = new JsonObject().put("sub", user.getName())
                        .put("role", new JsonArray(user.getRoleList()));
                JWTOptions jwtOptions = new JWTOptions().setExpiresInMinutes(30) ;
                String token = authProvider.generateToken(payload,jwtOptions);
                //登陆
                UserHelper.SINGLE.userLogin(user) ;
                JsonObject returnJson = new JsonObject()
                        .put("code",RestResult.CD1[0])
                        .put("desc",RestResult.CD1[1])
                        .put("data",token)
                        .put("loginCode",loginCode)
                        .put("name",user.getName())
                        ;
                JsonObject sendObject = new JsonObject() ;
                sendObject.put("to","") ;
                sendObject.put("messageEventType", MessageEventType.USER_LOGIN) ;
                vertx.eventBus().send("api.send.message.to.client",sendObject);
                ctx.response().end(returnJson.encode()) ;
            }
            else{
                JsonObject returnJson = new JsonObject()
                        .put("code",RestResult.CD101[0])
                        .put("desc",RestResult.CD101[1])
                        ;
                ctx.response().end(returnJson.encode()) ;
            }
        });


        ////启动WebServer
        HttpServerOptions httpopt   =   new HttpServerOptions()
                .setMaxWebsocketFrameSize(655360);//设置数据量的大小，在数据量小的时候，这个值可以不用设置
        HttpServer server=vertx.createHttpServer(httpopt);
        server.requestHandler(router::accept).listen(8082, res -> {
            if (res.succeeded()) {
                LOGGER.info("服务开启成功！");
            } else {
                LOGGER.info("服务开启失败");
            }
        });


    }
}
