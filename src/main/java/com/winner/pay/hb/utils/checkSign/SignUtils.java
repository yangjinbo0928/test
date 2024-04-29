package com.winner.pay.hb.utils.checkSign;

import org.apache.commons.codec.digest.DigestUtils;

//zbs
public class SignUtils {
	public static String sign(String clientId, String timeStamp, String appKey) {
		String appSecretKey = DigestUtils.md5Hex(appKey);
		// 签名
		return DigestUtils.md5Hex("ClientId=" + clientId + "&Param=" + timeStamp + "&AppSecretKey=" + appSecretKey);
	}
}
