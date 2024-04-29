package com.winner.pay.abc;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;



public class sm4 {
	 private static final int ENCRYPT = 1;
	    private static final int DECRYPT = 0;
	    public static final int ROUND = 32;
	    private static final int BLOCK = 16;
	    private byte[] Sbox = new byte[]{-42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, 44, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, -128, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, -125, 89, 60, 25, -26, -123, 79, -88, 104, 107, -127, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, 59, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, -126, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, -68, 127, 17, -39, 92, 65, 31, 16, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, -124, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
	    private int[] CK = new int[]{462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};


	    private int Rotl(int x, int y) {
	        return x << y | x >>> 32 - y;
	    }


	    private int ByteSub(int A) {
	        return (this.Sbox[A >>> 24 & 255] & 255) << 24 | (this.Sbox[A >>> 16 & 255] & 255) << 16 | (this.Sbox[A >>> 8 & 255] & 255) << 8 | this.Sbox[A & 255] & 255;
	    }


	    private int L1(int B) {
	        return B ^ this.Rotl(B, 2) ^ this.Rotl(B, 10) ^ this.Rotl(B, 18) ^ this.Rotl(B, 24);
	    }


	    private int L2(int B) {
	        return B ^ this.Rotl(B, 13) ^ this.Rotl(B, 23);
	    }


	    void SM4Crypt(byte[] Input, byte[] Output, int[] rk) {
	        int[] x = new int[4];
	        int[] tmp = new int[4];


	        int j;
	        for(j = 0; j < 4; ++j) {
	            tmp[0] = Input[0 + 4 * j] & 255;
	            tmp[1] = Input[1 + 4 * j] & 255;
	            tmp[2] = Input[2 + 4 * j] & 255;
	            tmp[3] = Input[3 + 4 * j] & 255;
	            x[j] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
	        }


	        for(int r = 0; r < 32; r += 4) {
	            int mid = x[1] ^ x[2] ^ x[3] ^ rk[r + 0];
	            mid = this.ByteSub(mid);
	            x[0] ^= this.L1(mid);
	            mid = x[2] ^ x[3] ^ x[0] ^ rk[r + 1];
	            mid = this.ByteSub(mid);
	            x[1] ^= this.L1(mid);
	            mid = x[3] ^ x[0] ^ x[1] ^ rk[r + 2];
	            mid = this.ByteSub(mid);
	            x[2] ^= this.L1(mid);
	            mid = x[0] ^ x[1] ^ x[2] ^ rk[r + 3];
	            mid = this.ByteSub(mid);
	            x[3] ^= this.L1(mid);
	        }


	        for(j = 0; j < 16; j += 4) {
	            Output[j] = (byte)(x[3 - j / 4] >>> 24 & 255);
	            Output[j + 1] = (byte)(x[3 - j / 4] >>> 16 & 255);
	            Output[j + 2] = (byte)(x[3 - j / 4] >>> 8 & 255);
	            Output[j + 3] = (byte)(x[3 - j / 4] & 255);
	        }


	    }
	public int[] getSM4Key(byte[] key, int cryptType) {
	        int[] rk = new int[32];
	    
	        int[] x = new int[4];
	        int[] tmp = new int[4];


	        for(int i = 0; i < 4; ++i) {
	            tmp[0] = key[0 + 4 * i] & 255;
	            tmp[1] = key[1 + 4 * i] & 255;
	            tmp[2] = key[2 + 4 * i] & 255;
	            tmp[3] = key[3 + 4 * i] & 255;
	            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
	        }


	        x[0] ^= -1548633402;
	        x[1] ^= 1453994832;
	        x[2] ^= 1736282519;
	        x[3] ^= -1301273892;


	        int r;
	        int mid;
	        for(r = 0; r < 32; r += 4) {
	            mid = x[1] ^ x[2] ^ x[3] ^ this.CK[r + 0];
	            mid = this.ByteSub(mid);
	            x[0] ^= this.L2(mid);
	            rk[r + 0] = x[0];
	            mid = x[2] ^ x[3] ^ x[0] ^ this.CK[r + 1];
	            mid = this.ByteSub(mid);
	            x[1] ^= this.L2(mid);
	            rk[r + 1] = x[1];
	            mid = x[3] ^ x[0] ^ x[1] ^ this.CK[r + 2];
	            mid = this.ByteSub(mid);
	            x[2] ^= this.L2(mid);
	            rk[r + 2] = x[2];
	            mid = x[0] ^ x[1] ^ x[2] ^ this.CK[r + 3];
	            mid = this.ByteSub(mid);
	            x[3] ^= this.L2(mid);
	            rk[r + 3] = x[3];
	        }


	        if (cryptType == 1) {
	            return rk;
	        } else if (cryptType != 0) {
	            return null;
	        } else {
	            for(r = 0; r < 16; ++r) {
	                mid = rk[r];
	                rk[r] = rk[31 - r];
	                rk[31 - r] = mid;
	            }


	            return rk;
	        }
	    }


	    public String encryptEcb(String plaintext, String keyHex) throws Exception {
	            return new String(Hex.encodeHex(this.encryptEcb(plaintext.getBytes(), Hex.decodeHex(keyHex.toCharArray()))));
	    }


	    public byte[] encryptEcb(byte[] plaintext, byte[] key) throws Exception {
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 1);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];


	            for(int plaintextLen = plaintext.length; plaintextLen > 0; point += 16) {
	                if (plaintextLen >= 16) {
	                    input = Arrays.copyOfRange(plaintext, point, point + 16);
	                } else {
	                    input = Arrays.copyOfRange(plaintext, point, point + plaintextLen);
	                    String zero = "                ";
	                    String tmpInput = new String(input) + zero.substring(0, 16 - input.length);
	                    input = tmpInput.getBytes();
	                }

	                this.SM4Crypt(input, output, round_key);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	    }


	    public String decryptEcb(String ciphertextHex, String keyHex) throws Exception {
	            return (new String(this.decryptEcb(Hex.decodeHex(ciphertextHex.toCharArray()), Hex.decodeHex(keyHex.toCharArray())))).trim();
	        
	    }
	
	    public byte[] decryptEcb(byte[] ciphertext, byte[] key) throws Exception {
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 0);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];


	            for(int plaintextLen = ciphertext.length; plaintextLen > 0; point += 16) {
	                if (plaintextLen >= 16) {
	                    input = Arrays.copyOfRange(ciphertext, point, point + 16);
	                } else {
	                    input = Arrays.copyOfRange(ciphertext, point, point + plaintextLen);
	                    String zero = "                ";
	                    String tmpInput = new String(input) + zero.substring(0, 16 - input.length);
	                    input = tmpInput.getBytes();
	                }


	                this.SM4Crypt(input, output, round_key);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	    }


	    public String encryptCbc(String plaintext, String keyHex, String ivHex) throws Exception {
	            return new String(Hex.encodeHex(this.encryptCbc(plaintext.getBytes(), Hex.decodeHex(keyHex.toCharArray()), Hex.decodeHex(ivHex.toCharArray()))));
	    }


	    public byte[] encryptCbc(byte[] plaintext, byte[] key, byte[] iv) throws Exception {
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 1);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];
	            byte[] inputTemp = new byte[16];


	            for(int plaintextLen = plaintext.length; plaintextLen > 0; point += 16) {
	                if (plaintextLen >= 16) {
	                    input = Arrays.copyOfRange(plaintext, point, point + 16);
	                } else {
	                    input = Arrays.copyOfRange(plaintext, point, point + plaintextLen);
	                    String zero = "                ";
	                    String tmpInput = new String(input) + zero.substring(0, 16 - input.length);
	                    input = tmpInput.getBytes();
	                }


	                for(int i = 0; i < 16; ++i) {
	                    inputTemp[i] = (byte)(input[i] ^ iv[i]);
	                }


	                this.SM4Crypt(inputTemp, output, round_key);
	                System.arraycopy(output, 0, iv, 0, 16);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	       
	    }


	    public String decryptCbc(String ciphertextHex, String keyHex, String ivHex) throws Exception {
	       
	            return (new String(this.decryptCbc(Hex.decodeHex(ciphertextHex.toCharArray()), Hex.decodeHex(keyHex.toCharArray()), Hex.decodeHex(ivHex.toCharArray())))).trim();
	     
	    }


	    public byte[] decryptCbc(byte[] ciphertext, byte[] key, byte[] iv) throws Exception {
	      
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 0);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];
	            byte[] outputTemp = new byte[16];


	            for(int plaintextLen = ciphertext.length; plaintextLen > 0; point += 16) {
	                if (plaintextLen >= 16) {
	                    input = Arrays.copyOfRange(ciphertext, point, point + 16);
	                } else {
	                    input = Arrays.copyOfRange(ciphertext, point, point + plaintextLen);
	                    String zero = "                ";
	                    String tmpInput = new String(input) + zero.substring(0, 16 - input.length);
	                    input = tmpInput.getBytes();
	                }


	                this.SM4Crypt(input, output, round_key);


	                for(int i = 0; i < 16; ++i) {
	                    outputTemp[i] = (byte)(output[i] ^ iv[i]);
	                }


	                System.arraycopy(input, 0, iv, 0, 16);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(outputTemp, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	      
	    }


	    public String encryptEcbWithPadding(String plaintext, String keyHex, Boolean paddingFlag) throws Exception {
	       
	            return new String(Hex.encodeHex(this.encryptEcbWithPadding(plaintext.getBytes(), Hex.decodeHex(keyHex.toCharArray()), paddingFlag)));
	        
	    }


	    public byte[] encryptEcbWithPadding(byte[] plaintext, byte[] key, Boolean paddingFlag) throws Exception {
	       
	            byte[] plaintextAfterPadding = new byte[(plaintext.length / 16 + 1) * 16];
	            if (paddingFlag) {
	                plaintextAfterPadding = SMUtil.PKCS7Padding(plaintext, plaintext.length);
	            } else {
	                if (plaintext.length % 16 != 0) {
	                    throw new Exception("encryptEcb:error input length!");
	                }


	                plaintextAfterPadding = plaintext;
	            }


	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 1);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];


	            for(int plaintextLen = plaintextAfterPadding.length; plaintextLen > 0; point += 16) {
	                input = Arrays.copyOfRange(plaintextAfterPadding, point, point + 16);
	                this.SM4Crypt(input, output, round_key);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	        
	    }


	    public String decryptEcbWithCutting(String ciphertextHex, String keyHex, Boolean cuttingFlag) throws Exception {
	       
	            return (new String(this.decryptEcbWithCutting(Hex.decodeHex(ciphertextHex.toCharArray()), Hex.decodeHex(keyHex.toCharArray()), cuttingFlag))).trim();
	       
	    }


	    public byte[] decryptEcbWithCutting(byte[] ciphertext, byte[] key, Boolean cuttingFlag) throws Exception {
	      
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 0);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];


	            for(int plaintextLen = ciphertext.length; plaintextLen > 0; point += 16) {
	                input = Arrays.copyOfRange(ciphertext, point, point + 16);
	                this.SM4Crypt(input, output, round_key);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            if (cuttingFlag) {
	                byte[] outAfterCutting = SMUtil.PKCS7Cutting(out, out.length);
	                return outAfterCutting;
	            } else {
	                return out;
	            }
	       
	    }


	    public String encryptCbcWithPadding(String plaintext, String keyHex, String ivHex, Boolean paddingFlag) throws Exception {
	      
	            return new String(Hex.encodeHex(this.encryptCbcWithPadding(plaintext.getBytes(), Hex.decodeHex(keyHex.toCharArray()), Hex.decodeHex(ivHex.toCharArray()), paddingFlag)));
	      
	    }


	    public byte[] encryptCbcWithPadding(byte[] plaintext, byte[] key, byte[] iv, Boolean paddingFlag) throws Exception {
	       
	            byte[] plaintextAfterPadding = new byte[(plaintext.length / 16 + 1) * 16];
	            if (paddingFlag) {
	                plaintextAfterPadding = SMUtil.PKCS7Padding(plaintext, plaintext.length);
	            } else {
	                if (plaintext.length % 16 != 0) {
	                    throw new Exception("encryptCbc:error input length!");
	                }


	                plaintextAfterPadding = plaintext;
	            }


	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 1);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];
	            byte[] inputTemp = new byte[16];


	            for(int plaintextLen = plaintextAfterPadding.length; plaintextLen > 0; point += 16) {
	                input = Arrays.copyOfRange(plaintextAfterPadding, point, point + 16);


	                for(int i = 0; i < 16; ++i) {
	                    inputTemp[i] = (byte)(input[i] ^ iv[i]);
	                }


	                this.SM4Crypt(inputTemp, output, round_key);
	                System.arraycopy(output, 0, iv, 0, 16);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(output, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            return out;
	       
	    }


	    public String decryptCbcWithCutting(String ciphertextHex, String keyHex, String ivHex, Boolean cuttingFlag) throws Exception {
	       
	            return (new String(this.decryptCbcWithCutting(Hex.decodeHex(ciphertextHex.toCharArray()), Hex.decodeHex(keyHex.toCharArray()), Hex.decodeHex(ivHex.toCharArray()), cuttingFlag))).trim();
	
	    }


	    public byte[] decryptCbcWithCutting(byte[] ciphertext, byte[] key, byte[] iv, Boolean cuttingFlag) throws Exception {
	            byte[] out = new byte[0];
	            byte[] old = new byte[0];
	            int point = 0;
	            int[] round_key = this.getSM4Key(key, 0);
	            byte[] input = new byte[16];
	            byte[] output = new byte[16];
	            byte[] outputTemp = new byte[16];


	            for(int plaintextLen = ciphertext.length; plaintextLen > 0; point += 16) {
	                input = Arrays.copyOfRange(ciphertext, point, point + 16);
	                this.SM4Crypt(input, output, round_key);


	                for(int i = 0; i < 16; ++i) {
	                    outputTemp[i] = (byte)(output[i] ^ iv[i]);
	                }


	                System.arraycopy(input, 0, iv, 0, 16);
	                old = out;
	                out = new byte[out.length + output.length];
	                System.arraycopy(old, 0, out, 0, old.length);
	                System.arraycopy(outputTemp, 0, out, old.length, 16);
	                plaintextLen -= 16;
	            }


	            if (cuttingFlag) {
	                byte[] outAfterCutting = SMUtil.PKCS7Cutting(out, out.length);
	                return outAfterCutting;
	            } else {
	                return out;
	            }
	    };
	    
	    
	    
}




