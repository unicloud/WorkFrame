package com.greatfly.common.util.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.greatfly.common.util.exception.AppException;


/**
 * MD5信息-摘要算法5，是一种单向加密算法（不可逆）</br>
 * MD5的算法在RFC1321 中定义
 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： 
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e 
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661 
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b 
 * @author wuwq
 * 2011-1-11 上午10:42:50
 */
public final class MD5Util {
	//用来将字节转换成16进制表示的字符
	private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	/**
	 * 私有构造函数，防止实例化使用
	 */
	private MD5Util() {}
	
	/**
	 * 对参数进行MD5加密
	 * @param bytes 要加密的参数
	 * @return MD5值
	 */
	public static String getMD5(byte[] bytes) {
		String result = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] temp = md.digest(); //MD5的计算结果是一个128位长整数，用字节表示就是16字节
			char[] chars = new char[32]; //每个字节用16进制表示的话，需要2个字符，所以共32个字符
			
			//对MD5的每个字节转换成16进制的字符
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte aByte = temp[i];
				chars[k++] = hexDigits[aByte >>> 4 & 0xf];
				chars[k++] = hexDigits[aByte & 0xf];
			}
			
			result = new String(chars);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 对参数进行MD5加密
	 * @param str 要加密的参数
	 * @return MD5值
	 * @throws UnsupportedEncodingException 
	 */
	public static String getMD5(String str) {
		try {
			return getMD5(str.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			throw new AppException("MD5加密异常", e);
		}
	}
	
}
