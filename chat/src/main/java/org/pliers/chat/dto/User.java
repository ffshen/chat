package org.pliers.chat.dto;

import java.util.List;

/**
 * @author shenx
 * @Description: 用户
 * @date 2018/3/20
 */
public class User {

    private String loginCode ;

    private String name ;

    private List<String> roleList ;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}
