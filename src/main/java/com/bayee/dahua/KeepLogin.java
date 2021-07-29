package com.bayee.dahua;

import java.util.Map;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;

/**
 * 会话保活接口
 */
@Service
@EnableAsync
public class KeepLogin extends BaseUserInfo
{
    public final String ACTION = "/videoService/accounts/token/keepalive";

    @Async
    public String doKeepAlive(String ip, int port, String token) throws Exception{
    	 String response=null;
    	try {
          	String content = "{\"token\":\""+token+"\"}";
             response=HttpTestUtils.httpRequest(HttpEnum.PUT,ip,port,ACTION,token,content);
           	
		} catch (Exception e) {
			e.printStackTrace();
		}
        return response;
    }
    
    //会话保活方法，保活时间为110s，该方法开启后不能关闭。
    @Async
    public void keepAlive (String ip, int port, String token, String  userName, String password) throws Exception {
        while (true) {
        	try {
        	       String rsp = doKeepAlive(ip, Integer.valueOf(port), token);
                   System.out.println("####### analysis Keep Alive response: \t"+rsp);
//                   if(StringUtils.isBlank(rsp)){
//                	   System.err.println("######为null重新登录####"+ip+":"+port+userName+password);
//                	   Map<String,Object> fromJson = gson.fromJson(Login.login(ip, port, userName, password),new TypeToken<Map<String,Object>>(){}.getType());
//           			 token = (String) fromJson.get("token");
//           			this.keepAlive(ip, port, token,userName , password);
//                   }
                   Thread.sleep(110000);
			} catch (Exception e) {
				e.printStackTrace();
			}
     
        }
    }
}



