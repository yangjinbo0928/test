package com.winner.pay.hb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSASignature {

	/**
	 * 随机生成密钥对
	 */
	public static void genKeyPair(String filePath) {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		if (keyPairGen != null) {
			keyPairGen.initialize(2048, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 得到私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			try {
				// 得到公钥字符串
				String publicKeyString = Base64.encode(publicKey.getEncoded());
				// 得到私钥字符串
				String privateKeyString = Base64.encode(privateKey.getEncoded());
				// 将密钥对写入到文件
				FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
				FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
				BufferedWriter pubbw = new BufferedWriter(pubfw);
				BufferedWriter pribw = new BufferedWriter(prifw);
				pubbw.write(publicKeyString);
				pribw.write(privateKeyString);
				pubbw.flush();
				pubbw.close();
				pubfw.close();
				pribw.flush();
				pribw.close();
				prifw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String loadKeyByFile(String path, String keyFile) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path + keyFile));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey     公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new NullPointerException("公钥不能为空");
		}
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int step = publicKey.getModulus().bitLength() / 8 - 11;
		int k = plainTextData.length / step;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (k > 0) {
			// 分页加密：每次加密step长度的字节
			for (int i = 0; i < k; i++) {
				int offset = i * step;
				out.write(cipher.doFinal(plainTextData, offset, step));
			}
			if ((plainTextData.length % step) != 0) {
				// 不能整除说明最后一页还有plainTextData.length-(step*k)个字节没加密
				out.write(cipher.doFinal(plainTextData, step * k, plainTextData.length - (step * k)));
			}
		} else {
			out.write(cipher.doFinal(plainTextData));
		}
		return out.toByteArray();
	}

	/**
	 * 私钥加密过程
	 * 
	 * @param privateKey    私钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		int step = privateKey.getModulus().bitLength() / 8 - 11;
		int k = plainTextData.length / step;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (k > 0) {
			// 分页加密：每次加密step长度的字节
			for (int i = 0; i < k; i++) {
				int offset = i * step;
				out.write(cipher.doFinal(plainTextData, offset, step));
			}
			if ((plainTextData.length % step) != 0) {
				// 不能整除说明最后一页还有plainTextData.length-(step*k)个字节没加密
				out.write(cipher.doFinal(plainTextData, step * k, plainTextData.length - (step * k)));
			}
		} else {
			out.write(cipher.doFinal(plainTextData));
		}
		return out.toByteArray();
	}

	/**
	 * 私钥解密过程
	 * 
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new NullPointerException("私钥不能为空");
		}
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int step = privateKey.getModulus().bitLength() / 8;// 256
		int k = cipherData.length / step;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (k > 0) {
			for (int i = 0; i < k; i++) {
				out.write(cipher.doFinal(cipherData, i * step, step));
			}
		} else {
			out.write(cipher.doFinal(cipherData));
		}
		return out.toByteArray();
	}

	/**
	 * 公钥解密过程
	 * 
	 * @param publicKey  公钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	public static RSAPrivateKey loadPrivateKeyByStr(String keyStr) throws Exception {
		try {
			byte[] buffer = Base64.decode(keyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

}
