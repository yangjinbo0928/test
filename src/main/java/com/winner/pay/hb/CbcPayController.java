package com.winner.pay.hb;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import com.winner.pay.hb.utils.OrderUtil;
import com.winner.pay.hb.utils.RSASignature;

@RestController
@RequestMapping("/v1")
public class CbcPayController extends BaseConstructor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	String path = System.getProperty("user.dir") + "/pay/";

	/**
	 * 加密
	 */
	@PostMapping("/api/encrypt")
	public Map<String, Object> couponReceive(@RequestBody Map<String, String> body) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "9999");
		logger.info("----------------------------------------begin----------------------------------------");
		try {
			System.out.println(path);
			String publicKey = RSASignature.loadKeyByFile(path, "hx_publicKey.keystore");
			String ecryptedResult = OrderUtil.encrypt(publicKey, body.get("comitDate"), StandardCharsets.UTF_8);
			map.put("code", "0000");
			map.put("data", ecryptedResult);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "9999");
			map.put("info", "请求异常");
		}
		logger.info("----------------------------------------end----------------------------------------");
		return map;
	}

	/**
	 * 解密
	 */
	@PostMapping("/api/decrypt")
	public Map<String, Object> couponStatusQuery(@RequestBody Map<String, String> body) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "9999");
		logger.info("----------------------------------------begin----------------------------------------");
		try {
			System.out.println(path);
			String privateKey = RSASignature.loadKeyByFile(path, "bfm_privateKey.keystore");
			String decryptedResult = OrderUtil.decrypt(privateKey, body.get("ecryptedResult"), StandardCharsets.UTF_8);
			map.put("code", "0000");
			map.put("data", decryptedResult);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "9999");
			map.put("info", "请求异常");
		}
		logger.info("----------------------------------------end----------------------------------------");
		return map;
	}

}
