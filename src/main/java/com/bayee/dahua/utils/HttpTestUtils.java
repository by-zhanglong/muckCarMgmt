package com.bayee.dahua.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.Login;

public class HttpTestUtils {
	public final static Logger log = Logger.getLogger(HttpTestUtils.class);

    public static String httpRequest(HttpEnum method,String ip, int port, String action, String token, String content) {
        String responJson = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        InputStream inputStream = null;
        try {
            httpClient = HttpClients.createDefault();
            String uri="http://" + ip + ":" + port + action;
            HttpRequestBase httpReq = getRequestEntity(method, token, uri, content);
            httpResponse = httpClient.execute(httpReq);
            inputStream = httpResponse.getEntity().getContent();
            responJson = convertToString(inputStream);
        } catch (UnsupportedEncodingException e) {
        	log.error("请求异常:"+e.getMessage());
        } catch (Exception e) {
       
            log.error("请求异常:"+e.getMessage());
        } finally {
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                	 log.error("请求异常:"+e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                	 log.error("请求异常:"+e.getMessage());
                }
            }
        }
        return responJson;
    }

    private static HttpRequestBase getRequestEntity(HttpEnum method, String token, String uri, String content) throws UnsupportedEncodingException {
        switch(method.getNum()){
            case 1:
                HttpGet httpGet = new HttpGet(uri+content);
                httpGet.addHeader("Content-type", "application/json");
                httpGet.addHeader("X-Subject-Token", token);
                return httpGet;
            case 2:
                HttpPost httpPost = new HttpPost(uri);
                httpPost.addHeader("Content-type", "application/json");
                httpPost.addHeader("X-Subject-Token", token);
                httpPost.setEntity(new StringEntity(content,"UTF-8"));
                return httpPost;
            case 3:
                HttpPut httpPut= new HttpPut(uri);
                httpPut.addHeader("Content-type", "application/json");
                httpPut.addHeader("X-Subject-Token", token);
                httpPut.setEntity(new StringEntity(content,"UTF-8"));
                return httpPut;
            case 4:
            	HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(uri);
                httpDelete.addHeader("Content-type", "application/json");
                httpDelete.addHeader("X-Subject-Token", token);
                httpDelete.setEntity(new StringEntity(content,"UTF-8"));
                return httpDelete;
            default:
                    System.out.println("请求方法不对");
        }
        return null;
    }

    private static String convertToString(InputStream is) {
        if (is == null) {
            return null;
        }
        BufferedReader bf = null;
        try {
            StringBuilder sb = new StringBuilder();
            String temp = "";
            bf = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            while ((temp = bf.readLine()) != null) {
                sb.append(temp);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("InputStream转字符串失败！");
            return null;
        } finally {
            closeStream(bf);
            closeStream(is);
        }
    }

    private static void closeStream(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("请求资源关闭失败！");
            }
        }
    }

    public static String getToken(String ip,int port,String userName,String password) throws Exception {
        String response="";
        String token="";
        response = Login.login(ip, port, userName, password);
        Map<String, Object> rsp = JSONObject.parseObject(response, Map.class);
        String message= (String)rsp.get("message");
        if (message!=null&&!"".equals(message)){
          //  System.out.println(message);
            throw new Exception("未获取到token");
        }
        token = (String)rsp.get("token");
        if(token==null||"".equals(token)){
            System.out.println("获取到的token为空");
            throw new Exception("获取到的token为空");
        }
        return  token;
    }

}
