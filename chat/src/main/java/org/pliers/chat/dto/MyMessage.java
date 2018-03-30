package org.pliers.chat.dto;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.pliers.chat.enumclass.MessageEventType;
import org.pliers.chat.enumclass.MessageType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author shenx
 * @Description: 消息
 * @date 2018/3/22
 */
public class MyMessage {

    private MessageType messageType ;

    private MessageEventType messageEventType ;

    private String from ;

    private String to ;

    private List<String> toList ;

    private String message ;

    public MyMessage(JsonObject jsonObject){
        this.from = jsonObject.getString("from") ;
        this.message = jsonObject.getString("message") ;
        this.to = jsonObject.getString("to") ;
        Optional.ofNullable(jsonObject.getJsonArray("toList") )
                .ifPresent(jsonArray->setToList(jsonArray.getList()));
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageEventType getMessageEventType() {
        return messageEventType;
    }

    public void setMessageEventType(MessageEventType messageEventType) {
        this.messageEventType = messageEventType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", toList=" + toList +
                ", message='" + message + '\'' +
                '}';
    }
}
