package com.bayee.dahua.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

/**
 * �ڶ��ε�¼����bean�ͷ���
 */
public class LoginSecond {
    private String userName;
    private String clientType;
    private String ipAddress;
	private String signature;
	private String randomKey;
	private String encryptType;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getRandomKey() {
        return randomKey;
    }
    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }
    public String getEncryptType() {
        return encryptType;
    }
    public String setEncryptType(String encryptType) {
        return this.encryptType = encryptType;
    }

	public static boolean isBlankString(String str) {
		if (str == null || str == "" || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static String encrypt(String inputText, String algorithmName) throws Exception {
		if (isBlankString(inputText)) {
			throw new IllegalArgumentException("Please enter inputText!");
		}
		if (isBlankString(algorithmName)
				|| algorithmName.toLowerCase().equals("md5")) {
			algorithmName = "MD5";
		}
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithmName);
			digest.update(inputText.getBytes("UTF8"));
			byte encryptted[] = digest.digest();
			int i;
			StringBuffer rsp = new StringBuffer();
			for (int offset = 0; offset < encryptted.length; offset++) {
				i = encryptted[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					rsp.append("0");
				rsp.append(Integer.toHexString(i));
			}
			return rsp.toString();
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}
	//���ݵ�һ�ε�¼�ӿڵ��÷��صĲ���������ǩ�����ڶ��ε�¼�ӿڵ���Ҫʹ�õ����������õ���MD5�ļ��ܷ�ʽ�����Ը�����Ҫ���ñ�ļ��ܷ�ʽ
	public String calcSignature(String password, String realm) throws Exception{
		String signature = encrypt(password, "MD5");
		signature = encrypt(this.userName+signature, "MD5");
		signature = encrypt(signature, "MD5");
		signature = encrypt(this.userName+":"+realm+":"+signature, "MD5");
		signature = encrypt(signature+":"+this.randomKey, "MD5");
		return signature;
	}

}

