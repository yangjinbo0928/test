package com.winner.pay.hb.utils.checkSign;

public class SignTest {

	public static void main(String[] args) {
		// 第三方传过来的签名
		String sign1 = "e6f1a5572a495071e951472b5956cf2f";
		// 获取验签秘钥
		String appKey = "a3f942a77baecb13a83dac9e580ab1ea";
		String clientId = "KY8TCXdVcJYYv7KR7h9URHXRIGHTS";
		String timestamp = "20210611100133";

//        appId:"tkafk8qklunhtjk"
//        clientOrderId:"1471975"
//        timestamp:"20210611100133"
//        version:"1.0"
		// 验签
		String sign = SignUtils.sign(clientId, timestamp, appKey);
		// 如果自己生成的签名与第三方传的不一致，则验签失败
		if (!sign.equalsIgnoreCase(sign1)) {
			System.out.println("验签失败，签名错误！");
		}

	}

}
