package org.pliers.chat.helper;

import org.pliers.chat.dto.User;
import org.pliers.chat.enumclass.Role;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author shenx
 * @Description: 用户
 * @date 2018/3/20
 */
public enum UserHelper {

    SINGLE ;

    private static TreeMap<String,User> userMap = new TreeMap<>() ;

    private static TreeMap<String,User> loginMap = new TreeMap<>() ;

    static {
        User user = new User() ;
        user.setLoginCode("4xMcnz");
        user.setName("shenx");
        user.setRoleList(Arrays.asList(Role.CHAT_ROOM.text,Role.FAMILY.text));
        userMap.put(user.getLoginCode(),user);
        //
        user = new User() ;
        user.setLoginCode("XPCUwG");
        user.setName("tiffany");
        user.setRoleList(Arrays.asList(Role.FAMILY.text));
        //
        userMap.put(user.getLoginCode(),user);
    }

    public static User getUser(String loginCode){
        return userMap.get(loginCode) ;
    }

    public static void userLogin(User user){
        loginMap.put(user.getName(),user) ;
    }
    public static List<User> getLoginUser(){
        return loginMap.values().parallelStream().collect(Collectors.toList()) ;
    }
}
