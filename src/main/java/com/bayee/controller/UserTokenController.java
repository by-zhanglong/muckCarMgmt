package com.bayee.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bayee.dahua.KeepLogin;
import com.bayee.dahua.utils.BaseUserInfo;
@CrossOrigin()
@Controller
@RequestMapping("/token")
public class UserTokenController {
	@Autowired
	private KeepLogin keepLogin;
	
	@RequestMapping(value="/keepAlive",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void keepAlive(HttpServletRequest httpReq) {
		String token=httpReq.getHeader("token");
		if(!StringUtils.isEmpty(token)) {
			 token=token.replace(" ","+");
			 try {
				keepLogin.doKeepAlive(BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),token);
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
