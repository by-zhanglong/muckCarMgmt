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
 * ���ٻỰ�ӿ�
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
//�ǳ��ķ���������token���˴�Ϊģ��ǳ���������������Խӿ�ʱ����Բ�ִ��
    public static void main(String[] args) throws Exception {
//        String rsp = loginOut(ip, Integer.valueOf(port), token);
    	  tokenPro();
    	  System.out.println("�˳��ɹ�");
    }
}



