package com.winner.pay.hb.utils;

import java.nio.charset.StandardCharsets;

public class RsaTest {

	public static void main(String[] args) throws Exception {
//		RSASignature.genKeyPair("D:\\project\\java\\RsaAndSignDemo");
		// 获取验签秘钥
//		String appKey = "1111";
//		String clientId = "121212";
//		String timestamp = "216545611";
//		//验签
//		String sign = RSAmain.signUtil(clientId, timestamp, appKey);
//		  System.out.println(sign);
//		String filepath="D:\\project\\java\\RsaAndSignDemo\\config";
//		String publicfile="/publicKey.keystore";
//		String encomitDate = "idNum=110101199001011114&idType=01&openId=oOaoQv8SRGYXqMbmJmnTy0Hbbzag&recAmt=150.00&tranDt=20190417200557";
//		String decomitDate=RSAmain.ncode(filepath,publicfile,encomitDate);
//		System.out.println("加密："+decomitDate);
//		String privatefile="/privateKey.keystore";
//		String decodeResult=RSAmain.decode(filepath,privatefile,decomitDate);
//		System.out.println("解密："+decodeResult);

		String path = System.getProperty("user.dir") + "\\pay\\dev\\";

//  	  	//生成密钥对
//  	     RSASignature.genKeyPair(filepath);
		// 需要加密的字符串
		String comitDate = "idNum=110101199001011114&idType=01&openId=oOaoQv8SRGYXqMbmJmnTy0Hbbzag&recAmt=150.00&tranDt=20190417200557";
		System.out.println("原文：" + comitDate);
//
		System.out.println("--------------公钥加密过程（银行端数据加密-CUPD提供）-------------------");
//		//获取公钥，通过文件获取或者数据库获取，此处用文件获取
		System.out.println(path + "bfm_publicKey.keystore");
		String publicKey = RSASignature.loadKeyByFile(path, "bfm_publicKey.keystore");
		String ecryptedResult = OrderUtil.encrypt(publicKey, comitDate, StandardCharsets.UTF_8);
		System.out.println("加密：" + ecryptedResult);

		ecryptedResult="BYFLhDkuUCXtMXpKh5%2FC4rRAaM%2BNUHYW9AyLeMCbmafDjMwVcV%2FUx3OHJQ8InqVPYyNMZBf0Ckjgw9Dkw8XXqNjjepYNUhCOOyKE%2B7ZkQsPNy%2BKPohWkdHh3pcIlILyhDXrcNwyKOGenZsYjfpYlvYPBOlGqwh2iHuFRb3lEcM0cvqAQoha1n1nm8GfIUxwrLmmCtrb43lsKg7qEy64CLyXAbBg7THP9PfNeNY1sdIXealzRwKEHe7UIUdIR0gNFBJn1wpW3EHjLSgYr91svASfEscONjzWV4K5DqLF0urWuxBMhtT9o1AMS%2FRqsb47vAZW%2FTaxmMZPeeIeVGwwxxA9TOnS%2BVY5mPju3h2W63xh4O44tfUN0Fepiv1YMQ37WwPxu6u8VQiu24X1hI7zhsbOEIp%2BNKM%2F3b3lBOJ%2Bh2aLOFcnP5v9Sye%2BF%2F2LVnDGGpWUPGXYRJiKNNVvURhrC9%2F8sn5yfMmo6vncVPEKKdYnm6fEc4sqzCXUVqNSC0rQQ2APsD8ImZYQMGwHixF7mWjBjGdtGTWbGXnDipV2OJJbO%2FR8a2D92OsSP%2Bbuqq9vI3juvcGJ5O7UOa1ztGiLAv62OwAdrDdxb8JOjGdT5oKPKJrUdjIXNjTPvm0fK7lspdFX0l0UPxpGuXbC3Sp9MoA1RbzAQEe8pridwKpuaIv0%3D";
//
		System.out.println("---------------私钥解密过程（CUPD 解密数据-CUPD提供）------------------");
////		//获取私钥
		String privateKey = RSASignature.loadKeyByFile(path, "bfm_privateKey.keystore");
		String decryptedResult = OrderUtil.decrypt(privateKey, ecryptedResult, StandardCharsets.UTF_8);
		System.out.println("解密：" + decryptedResult);
	}
	// 加密：U1%2FZGJhrbHDsuRU6c6LUkZZ0gSzQ%2BTyckWklAdWI75epOjDD%2Bi4HAWQnqvP2KjzexVsjmbZK5XdiOUo6ALbJFeWmEwGNgX7imzNuhpils31%2FJ5y3Ty8SW%2F09YZPEsCAZ1tNBXj9MULzYCtdO7MD1j9%2Bmo3MzLKk8H9Xq8LxqT5Yvz6TWKeR7JrXu0frTjEw9VPcEhbRJk5dfvvIBEdUTEpUF6nSreNj5NWXH8G4qVLHy2v5kSuMYCfJ2th9pDobCGOzv0EsPyf0KNnhSsLnLXHdLfYvjcwnfwaXXsugk7XTR6K3S8Wn%2BwJH61CbHmVErpb6LvTM%2Fsk5d4d58ys2JkQ%3D%3D
//加密：MhoN%2Bn1nxoI5%2FZUWYAObyHKT9Jy6EmLFsSw5IG80SU03UqvCgu%2Fql3Pg2qPZ%2Fep3WS68Y3SlK35p7BiSaV4nmlt1qUUx9puhc1U1TRoNlvKza57qeCdDKMO2UUxOaBKmNSI0DokkDD0OE9bFF%2F3YFm8Q7%2Blq%2BQKK%2BXGlCzFiEYEypJjQeNZ71UISS1ORjUWSPLAE520x%2FaeDTxII85FX2%2FNkkfkFcgVbQB1BXdVinrqgQoDpSyTKO7L%2FnAocqkGQfsEDlqhGIZ5Sl3x0iTgoAyd40ESQ2se6I%2BawSAF5DhUfx79osnv%2F0orH5k1w45FmcYWm4Y%2BRhzHZlEVH%2Fg7J%2Fg%3D%3D

}
