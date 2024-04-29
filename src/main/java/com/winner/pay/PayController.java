package com.winner.pay;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import com.alibaba.fastjson2.JSON;

@RestController
@RequestMapping("/pay")
public class PayController extends BaseConstructor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/geteway")
	public String geteway(@RequestParam Map<String, String> map) {
		return "hello winner geteway - post - Controller!!!";
	}

	/**
	 * @param urlparams
	 * @return
	 */
	@PostMapping("/urlparams")
	public String urlparams(@RequestBody Map<String, String> body, @RequestHeader MultiValueMap<String, String> headers) {
		logger.info("----------------------------------------begin----------------------------------------");
		String jsonStr = JSON.toJSONString(body);
		logger.info("urlparams.do.in\n" + jsonStr);
		String resStr = jsonStr;
		logger.info("urlparams.do.out\n" + resStr);
		logger.info("----------------------------------------end----------------------------------------");
		headers.forEach((key, value) -> {
			logger.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("/"))));
		});
		return resStr;
	}

	/**
	 * bz通知
	 * 
	 * @param noticebc
	 * @return
	 */
	@PostMapping("/notice")
	public String notice(@RequestParam Map<String, String> map) {
		logger.info("----------------------------------------begin----------------------------------------");
		String jsonStr = JSON.toJSONString(map);
		logger.info("pay.notice.in\n" + jsonStr);
		String resStr = jsonStr;
		logger.info("pay.notice.out\n" + resStr);
		logger.info("----------------------------------------end----------------------------------------");
		return resStr;
	}

	/**
	 * bz通知
	 * 
	 * @param noticebc
	 * @return
	 */
	@PostMapping("/noticejson")
	public String noticejson(@RequestBody Map<String, String> map) {
		logger.info("----------------------------------------begin----------------------------------------");
		String jsonStr = JSON.toJSONString(map);
		logger.info("pay.notice.in\n" + jsonStr);
		String resStr = jsonStr;
		logger.info("pay.notice.out\n" + resStr);
		logger.info("----------------------------------------end----------------------------------------");
		return resStr;
	}


}
