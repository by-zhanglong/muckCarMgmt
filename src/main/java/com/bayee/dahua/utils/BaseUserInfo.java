package com.bayee.dahua.utils;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public  class BaseUserInfo {
    public static String ip;
    public static String port;
    public static String userName;
    public static String password;
    public static String ipAddress;
    public static String repositoryCode;
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("baseinfo");
        ip = bundle.getString("ip");
        port = bundle.getString("port");
        userName = bundle.getString("user");
        password = bundle.getString("password");
        ipAddress = bundle.getString("ipAddress");
        repositoryCode=bundle.getString("repositoryCode");
    }
    
    public String getToken() {
    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        return (String)application.getAttribute("token");
    }
    
    public String getName() {
    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = webApplicationContext.getServletContext();
        return (String)application.getAttribute("name");
    }
}
