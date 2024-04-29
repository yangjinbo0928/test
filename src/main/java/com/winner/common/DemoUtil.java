package com.winner.common;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson2.JSON;

/**
 * 
 * @author dxw
 * 
 */
public class DemoUtil {

	private static Logger logger = LoggerFactory.getLogger(DemoUtil.class);

	public static final String SYSPATH = System.getProperty("user.dir") + "\\src\\main\\resources\\update\\";

	// 编码，一般固定用UTF-8就行
	public final static String encoding = "UTF-8";

	public static void main(String[] args) {
//		System.out.println(getOrderId());
//		System.out.println(getRandomString(22));

		logger.info(DemoUtil.SYSPATH);
	}

	/**
	 * 
	 * @description getTxnTime：时间 格式:yyyyMMddHHmmss
	 * @author 创建人：dxw
	 * @date 时间：2021年6月4日-上午8:53:56
	 * @return String
	 * @exception @since 1.0.0
	 */
	public static String getTxnTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	

	private static AtomicInteger orderIdCounter = new AtomicInteger(0);

	/**
	 * 
	 * @description generateOrderId：服务启动生成订单号
	 * @author 创建人：dxw
	 * @date 时间：2021年6月4日-上午8:54:19
	 * @return String
	 * @exception @since 1.0.0
	 */
	private static String generateOrderId() {
		return String.format("%06d", orderIdCounter.getAndIncrement() % 1000000);
	}

	// AN8..40 商户订单号，不能含"-"或"_"
	public static String getOrderId() {
		return getTxnTime() + generateOrderId();
	}

	public static String getRandomString() {
		return getRandomString(10);
	}

	/**
	 * 
	 * @description getRandomString：生成随机数a-zA-Z1-0
	 * @author 创建人：dxw
	 * @date 时间：2021年6月4日-上午8:54:49
	 * @param length
	 * @return String
	 * @exception @since 1.0.0
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString().toUpperCase();
	}

	public static Map<String, String> requestToParamsMap(final ServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		// 获取支付宝POST过来反馈信息
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
//			try {
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				logger.info("签约乱码异常" + e);
//			}
			params.put(name, valueStr);
		}
		return params;
	}

	/**
	 * 拼接字符串
	 * 
	 * @description createLinkString：TODO（用一句话描述这个变量表示什么）
	 * @author 创建人：dxw
	 * @date 时间：2021年6月21日-上午10:41:05
	 * @param couponMaps
	 * @param flag
	 * @return String
	 * @exception @since 1.0.0
	 */
	public static String createLinkString(Map<String, Object> couponMaps, String flag) {
		List<String> keys = new ArrayList<String>(couponMaps.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = couponMaps.get(key) == null ? "" : couponMaps.get(key).toString();
			if ("params".equals(flag)) {
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
					prestr = prestr + key + "=" + value;
				} else {
					prestr = prestr + key + "=" + value + "&";
				}
			} else if ("str".equals(flag)) {
				prestr = prestr + key + value.replaceAll("(?:\\[|null|\\]| +)", "");
			}
		}
		return prestr;
	}

	/**
	 * 转md5
	 * 
	 * @description toMD5：TODO（用一句话描述这个变量表示什么）
	 * @author 创建人：dxw
	 * @date 时间：2021年6月21日-上午10:41:14
	 * @param plainText
	 * @return String
	 * @exception @since 1.0.0
	 */
	public static String toMD5(String plainText) {
		String result = "";
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
			// System.out.println("16位: " + buf.toString().substring(8, 24));//
			// 16位的加密，其实就是32位加密后的截取
		} catch (Exception e) {
			logger.error("md5错误：" + e);
		}

		return result;
	}

	public static String md5DateToStr() {
//			KFLMURHEHES7TXUA
//			{ 
//				"appId": "78154652115",
//				"orderId": "86566332554656482",
//				"undoOrderId": "86566332554656486",
//				"couponCodes": [
//				                "15978089801348700038",
//				                "15978089801355426713",
//				                "15978089801365729509"],
//				"phone": "18682972086",
//				"nonceStr": "238948302",
//				"timestamp": "1809984938438",
//				"sign": "620b1f032736ebcf67441c6ecad52e4c"
//			}
//			appId78154652115couponCodes15978089801348700038,15978089801355426713,15978089801365729509nonceStr238948302orderId86566332554656482phone18682972086timestamp1809984938438undoOrderId86566332554656486KFLMURHEHES7TXUA
//			620b1f032736ebcf67441c6ecad52e4c

		/**
		 * appId78154652115couponCodes15978089801348700038,15978089801355426713,15978089801365729509nonceStr238948302orderId86566332554656482phone18682972086timestamp1809984938438undoOrderId86566332554656486KFLMURHEHES7TXUA
		 * {"couponCodes":"[15978089801348700038, 15978089801355426713,
		 * 15978089801365729509]","undoOrderId":"86566332554656486","springsign":"620b1f032736ebcf67441c6ecad52e4c","orderId":"86566332554656482","phone":"18682972086","appId":"78154652115","nonceStr":"238948302","javasign":"620b1f032736ebcf67441c6ecad52e4c","timestamp":"1809984938438"}
		 */

		String appKey = "KFLMURHEHES7TXUA";
		Map<String, Object> couponMaps = new HashMap<String, Object>();
		List<String> strList = new ArrayList<String>();
		strList.add("15978089801348700038");
		strList.add("15978089801355426713");
		strList.add("15978089801365729509");

		couponMaps.put("appId", "78154652115");
		couponMaps.put("orderId", "86566332554656482");
		couponMaps.put("undoOrderId", "86566332554656486");
		couponMaps.put("couponCodes", strList.toString()); // .replaceAll("(?:\\[|null|\\]| +)", ""
		couponMaps.put("phone", "18682972086");
		couponMaps.put("nonceStr", "238948302");
		couponMaps.put("timestamp", "1809984938438");

		StringBuffer sbStr = new StringBuffer(DemoUtil.createLinkString(couponMaps, "str"));
		sbStr.append(appKey);
		System.out.println(sbStr.toString());

		// spring 版本
		String springsign = DigestUtils.md5DigestAsHex(sbStr.toString().getBytes()).toString();
		String javasign = DemoUtil.toMD5(sbStr.toString());
		couponMaps.put("springsign", springsign);
		couponMaps.put("javasign", javasign);

		System.out.println(JSON.toJSONString(couponMaps).toString());

		/**
		 * appId78154652115couponCodes15978089801348700038,15978089801355426713,15978089801365729509nonceStr238948302orderId86566332554656482phone18682972086timestamp1809984938438undoOrderId86566332554656486KFLMURHEHES7TXUA
		 * {"couponCodes":"[15978089801348700038, 15978089801355426713,
		 * 15978089801365729509]","undoOrderId":"86566332554656486","springsign":"620b1f032736ebcf67441c6ecad52e4c","orderId":"86566332554656482","phone":"18682972086","appId":"78154652115","nonceStr":"238948302","javasign":"620b1f032736ebcf67441c6ecad52e4c","timestamp":"1809984938438"}
		 */
		return JSON.toJSONString(couponMaps).toString();
	}
	
	
	public static void logs(Logger logs, String url, String str1, String str2) {
		logs.info("\n"
				+ "请求地址 - " + url
				+ "\n"
				+ "请求参数 - " + str1
				+ "\n"
				+ "响应参数 - " + str2);
	}
	
	
}