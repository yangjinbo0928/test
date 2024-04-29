package com.winner.pay.abc;

import org.bouncycastle.crypto.digests.SM3Digest;

public class sm3 {
		public static String sm3Sign(String srcData) {
			byte[] srcDataByte = srcData.getBytes();
			SM3Digest sm3Digest = new SM3Digest();
			sm3Digest.update(srcDataByte, 0, srcDataByte.length);
			byte[] encryptByte = new byte[sm3Digest.getDigestSize()];
			sm3Digest.doFinal(encryptByte, 0);
			return byte2hexString(encryptByte).toUpperCase();
		}
		
		/**
		 * !转字符串
		 * 
		 * @param bytes
		 * @return
		 */
		public static String byte2hexString(byte[] bytes) {
			String r = "";

			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(bytes[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				r += hex + "";
			}
			return r;
		}

}
