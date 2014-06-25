package com.xiaopiao.password.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * util for secret
 * 
 * @author qinchaowei
 * 
 */
public class SecretUtil {

	/**
	 * encrypt a string to SHA1 value
	 */
	public static String SHA1(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1"); // ѡ��SHA-1��Ҳ����ѡ��MD5
			byte[] digest = md.digest(inStr.getBytes()); // ���ص���byet[]��Ҫת��ΪString�洢�ȽϷ���
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}

	private static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}
}
