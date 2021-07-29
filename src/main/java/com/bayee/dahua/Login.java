package com.bayee.dahua;//package com.dahuatch.Login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.bean.LoginFirst;
import com.bayee.dahua.bean.LoginSecond;
import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;

/**
 * 创建会话接口
 */
public class Login extends BaseUserInfo
{
	public static final String ACTION = "/videoService/accounts/authorize";
	//第一次登陆，客户端只传用户名，服务端返回realm、readomKey和encryptType信息。
	private static String firstLogin(String ip, int port, String userName){
		LoginFirst loginFirst = new LoginFirst();
	    loginFirst.setClientType("winpc");
	    loginFirst.setUserName(userName);
	    loginFirst.setIpAddress(ipAddress);
		String rsp=HttpTestUtils.httpRequest(HttpEnum.POST,ip,port,ACTION,"",JSONObject.toJSONString(loginFirst));
		return rsp;
	}
	//第二次登录，客户端根据返回的信息，按照指定的加密算法计算签名，再带着用户名和签名登陆一次。
	private static String secondLogin(String ip, int port, String userName, String password, String realm, String randomKey) throws Exception{
		LoginSecond snd = new LoginSecond();
		snd.setUserName(userName);
		snd.setClientType("winpc");
		snd.setRandomKey(randomKey);
		snd.setEncryptType("MD5");
		String signature = snd.calcSignature(password, realm);
		snd.setSignature(signature);
		String ctx = JSONObject.toJSONString(snd);
		String rsp=HttpTestUtils.httpRequest(HttpEnum.POST,ip,port,ACTION,"",ctx);
		System.out.println("#################### 2 SECOND LOGIN " + rsp);
		return rsp;
	}

	@SuppressWarnings("unchecked")
	public static String login(String ip, int port, String userName, String password)throws Exception {
		String response = firstLogin(ip, port, userName);
		System.out.println("################## 1 FIRST LOGIN [ip:" + ip +", port"+port+", userName:" + userName +", password: "+ password +" \n" + response);
		Map<String, String> responseMap = JSONObject.parseObject(response, Map.class);
		String random = responseMap.get("randomKey");
		String realm = responseMap.get("realm");
		response = secondLogin(ip, port, userName, password, realm, random);
		return response;
	}

//	@SuppressWarnings("unchecked")
//	private static void tokenPro() {
//		Properties pro=new Properties();
//		pro.setProperty("token",getToken());
//		try {
//			File file=new File("src/main/resources/token.properties");
//			OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(file));
//			pro.store(writer,"setToken");
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * 调用该登陆方法之前需要修改 baseinfo.properties中ip,port，name，password为当前对接环境
	 *	登录的方法，运行之后即可获取到token，进行登录,登陆完成后，必须调用KeepLogin保活接口，否则，2分钟后会登陆过期
	 *
	 */
	public static void main(String[] args) throws Exception {
	//	token = HttpTestUtils.getToken(ip, Integer.valueOf(port), userName, password);
		//tokenPro();
	//	System.out.println("登录成功，token="+token);
	}
}



