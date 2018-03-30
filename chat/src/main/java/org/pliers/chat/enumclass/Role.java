package org.pliers.chat.enumclass;

/**
 * @author shenx
 * @Description: 角色
 * @date 2018/3/20
 */
public enum Role {

    CHAT_ROOM(0, "聊天室"), FAMILY(1, "家庭");

    public String text;
    public Integer code;

    Role(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public static String getText(String value) {
        String text = "";
        for (Role role : Role.values()) {
            if (role.code.toString().equals(value)) {
                text = role.text;
            }
        }
        return text;
    }
}
