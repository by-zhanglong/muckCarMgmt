package com.bayee.dahua;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;

/**
 * 销毁会话接口
 */
class LoginOut extends BaseUserInfo
{
    public static final String ACTION = "/videoService/accounts/unauthorize";



    private static String loginOut(String ip, int port, String token) throws Exception{
        String content = "{\"token\":\""+token+"\"}";
        String response=HttpTestUtils.httpRequest(HttpEnum.POST,ip,port,ACTION,token,content);
        System.out.println(response);
        return response;
    }

    private static void tokenPro() {
        Properties pro=new Properties();
        pro.setProperty("token","");
        try {
            File file=new File("src/main/resources/token.properties");
            OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(file));
            pro.store(writer,"setToken");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//登出的方法，销毁token，此处为模拟登出的情况，正常测试接口时候可以不执行
    public static void main(String[] args) throws Exception {
//        String rsp = loginOut(ip, Integer.valueOf(port), token);
    	  tokenPro();
    	  System.out.println("退出成功");
    }
}



