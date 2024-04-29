package com.winner.pay.hb.utils;

import com.winner.pay.hb.utils.checkSign.SignUtils;

import java.nio.charset.StandardCharsets;

public class RSAmain {
	public static String ncode(String filepath, String publicfile, String encomitDate) throws Exception {
		// 生成密钥对
		RSASignature.genKeyPair(filepath);
		// 获取公钥，通过文件获取或者数据库获取，此处用文件获取
		String publicKey = RSASignature.loadKeyByFile(filepath, publicfile);
		String ecryptedResult = OrderUtil.encrypt(publicKey, encomitDate, StandardCharsets.UTF_8);
		return ecryptedResult;
	}

	public static String decode(String filepath, String privatefile, String decomitDate) throws Exception {
		// 生成密钥对
		// RSASignature.genKeyPair(filepath);
		// 获取公钥，通过文件获取或者数据库获取，此处用文件获取
		String privateKey = RSASignature.loadKeyByFile(filepath, privatefile);
		String decryptedResult = OrderUtil.decrypt(privateKey, decomitDate, StandardCharsets.UTF_8);
		return decryptedResult;
	}

	public static String signUtil(String appKey, String clientId, String timestamp) {
		String sign = SignUtils.sign(clientId, timestamp, appKey);
		return sign;
	}

}
