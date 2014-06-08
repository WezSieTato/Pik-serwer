package com.pik.moviecollection.server;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Marcin Stepnowski on 08.06.2014.
 */
public class SessionManager {
    private static final String TokenKey = "TokenKey";

    public static HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static String getToken(){
        return (String)getSession().getAttribute(TokenKey);
    }

    public static void setToken(String token){
        getSession().setAttribute(TokenKey, token);
    }
}
