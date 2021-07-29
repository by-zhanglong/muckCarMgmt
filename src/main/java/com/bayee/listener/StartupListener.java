package com.bayee.listener;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.KeepLogin;
import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;

public class StartupListener implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private KeepLogin keepLogin;
	 @Override  
     public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			 String token=HttpTestUtils.getToken(keepLogin.ip, Integer.parseInt(keepLogin.port), keepLogin.userName, keepLogin.password);
			 WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		     ServletContext application = webApplicationContext.getServletContext();
		     application.setAttribute("token", token);
		     String action="/ras/user/info";
			 String content="";
			 String resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
						action, token, content);
			 if (!StringUtils.isEmpty(resData)) {
					JSONObject jsonData = JSON.parseObject(resData);
					application.setAttribute("userName", jsonData.getString("userName"));
					application.setAttribute("name", jsonData.getString("name"));
				}
		     keepLogin.keepAlive(keepLogin.ip, Integer.parseInt(keepLogin.port),token, keepLogin.userName,  keepLogin.password);
		}  catch (Exception e) {
			e.printStackTrace();
		}
     }  
}
