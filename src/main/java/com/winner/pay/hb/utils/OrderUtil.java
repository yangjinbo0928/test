package com.winner.pay.hb.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 新前置工具类：加密、解密、封装数据等。
 * 
 * @author Jiaxz
 * @date 20190306
 */
public class OrderUtil {

	/**
	 * 使用默认UTF-8编码加密数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String filePath, String fileName, String data) throws Exception {
		return encrypt(filePath, fileName, data, StandardCharsets.UTF_8);
	}

	/**
	 * 使用默认UTF-8编码解密数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @param encryptString
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String filePath, String fileName, String encryptString) throws Exception {
		return decrypt(filePath, fileName, encryptString, StandardCharsets.UTF_8);
	}

	/**
	 * 根据指定的字符编码加密数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @param data
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String filePath, String fileName, String data, Charset charset) throws Exception {
		try {
			String keyStr = RSASignature.loadKeyByFile(filePath, fileName);
			RSAPublicKey publicKey = RSASignature.loadPublicKeyByStr(keyStr);
			byte[] cipherData = RSASignature.encrypt(publicKey, data.getBytes());
			String cipher = Base64.encode(cipherData);
			String result = URLEncoder.encode(cipher, charset.displayName());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 根据指定的字符编码解密数据
	 * 
	 * @param filePath
	 * @param fileName
	 * @param encryptString
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String filePath, String fileName, String encryptString, Charset charset) throws Exception {
		try {
			String keyStr = RSASignature.loadKeyByFile(filePath, fileName);
			RSAPrivateKey privateKey = RSASignature.loadPrivateKeyByStr(keyStr);
			String cipher = URLDecoder.decode(encryptString, charset.displayName());
			byte[] cipherData = RSASignature.decrypt(privateKey, Base64.decode(cipher));
			String result = new String(cipherData, charset);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 根据指定的字符编码解密数据
	 * 
	 * @param privateKeyStr
	 * @param encryptString
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String privateKeyStr, String encryptString, Charset charset) throws Exception {
		try {
			RSAPrivateKey privateKey = RSASignature.loadPrivateKeyByStr(privateKeyStr);
			String cipher = URLDecoder.decode(encryptString, charset.displayName());
			byte[] cipherData = RSASignature.decrypt(privateKey, Base64.decode(cipher));
			String result = new String(cipherData);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 根据指定的字符编码加密数据
	 * 
	 * @param publicKeyStr
	 * @param decryptString
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String publicKeyStr, String decryptString, Charset charset) throws Exception {
		try {
			RSAPublicKey publicKey = RSASignature.loadPublicKeyByStr(publicKeyStr);
			byte[] cipherData = RSASignature.encrypt(publicKey, decryptString.getBytes());
			String cipher = Base64.encode(cipherData);
			String result = URLEncoder.encode(cipher, charset.displayName());
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * 根据传入的日期格式、格式化日期
	 * 
	 * @param localDateTime
	 * @param pattern
	 * @return
	 */
	public static final String getSimpleDate(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		String tranDate = localDateTime.format(dateFormatter);
		return tranDate;
	}

	/**
	 * 根据传入的时间格式格式化时间
	 * 
	 * @param localDateTime
	 * @param pattern
	 * @return
	 */
	public static final String getSimpleTime(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(pattern);
		String tranTime = localDateTime.format(timeFormatter);
		return tranTime;
	}
}