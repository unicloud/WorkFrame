package com.greatfly.common.util.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.greatfly.common.util.exception.AppException;


/**
 * AES加密算法，采用同一个密钥进行加解密，是一种对称密钥加密算法。
 * @author wuwq
 * 2011-1-24 上午08:27:46
 */
public final class AESUtil {
	//密钥
	private final static String KEY_STR = "9Lj`3=+1dsVaxi&dnE";
	private final Key key;
	
	private static AESUtil instance = new AESUtil();
	/**
	 * 私有构造函数，防止通过实例化使用
	 */
	private AESUtil() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(new SecureRandom(KEY_STR.getBytes("GBK")));
			key = generator.generateKey();
			
		} catch (NoSuchAlgorithmException ne) {
			throw new AppException("AES构造函数异常", ne);
		} catch (UnsupportedEncodingException ue) {
			throw new AppException("AES构造函数异常", ue);
		}
	}
	
	public static AESUtil getInstance() {
		return instance;
	}
	
	/**
	 * 对byte[]参数进行加密
	 * @param bytes 要加密的参数
	 * @return 加密后的参数
	 */
	private byte[] getEncCode(byte[] bytes) {
		byte[] result = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			result = cipher.doFinal(bytes);
			
		} catch (Exception e) {
			throw new AppException("AES加密异常", e);
		} finally {
			cipher = null;
		}
		return result;
	}
	
	/**
	 * 对byte[]参数进行解密
	 * @param bytes 要解密的参数
	 * @return 解密后的参数
	 */
	private byte[] getDesCode(byte[] bytes) {
		byte[] result = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(bytes);
		} catch (Exception e) {
			throw new AppException("AES解密异常", e);
		} finally {
			cipher = null;
		}
		return result;
	}
	
	/**
	 * 对string参数进行加密
	 * @param str 要加密的参数
	 * @return 加密后的参数
	 */
	public String getEncString(String str) {
		BASE64Encoder base64en = new BASE64Encoder();
		byte[] input = null; //明文
		byte[] output = null; //密文
		String result = null;
		try {
			input = str.getBytes("UTF-8");
			output = getEncCode(input);
			result = base64en.encode(output);
			
		} catch (Exception e) {
			throw new AppException("AES解密异常，参数：" + str, e);
		} finally {
			input = null;
			output = null;
		}
		return result;
	}
	
	/**
	 * 对String参数进行解密
	 * @param str 要解密的参数
	 * @return 解密后的参数
	 */
	public String getDesString(String str) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] input = null; //密文
		byte[] output = null; //明文
		String result = null;
		try {
			input = base64De.decodeBuffer(str);
			output = getDesCode(input);
			result = new String(output, "UTF-8");
			
		} catch (Exception e) {
			throw new AppException("AES解密异常，参数：" + str, e);
		} finally {
			input = null;
			output = null;
		}
		return result;
	}
	
}
