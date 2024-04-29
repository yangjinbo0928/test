package com.winner.pay.abc;

public class SMUtil {
	public static byte[] PKCS7Padding(byte[] p, int plen) {
        int padding_num = 0;
        byte padding_value = 0;
        byte[] paddingResult = new byte[(plen / 16 + 1) * 16];
        if (plen % 16 == 0) {
            padding_num = 16;
            padding_value = 16;
        }


        if (plen == 0) {
            return null;
        } else {
            if (plen % 16 != 0) {
                int remain = plen % 16;
                switch(remain) {
                case 1:
                    padding_num = 15;
                    padding_value = 15;
                    break;
                case 2:
                    padding_num = 14;
                    padding_value = 14;
                    break;
                case 3:
                    padding_num = 13;
                    padding_value = 13;
                    break;
                case 4:
                    padding_num = 12;
                    padding_value = 12;
                    break;
                case 5:
                    padding_num = 11;
                    padding_value = 11;
                    break;
                case 6:
                    padding_num = 10;
                    padding_value = 10;
                    break;
                case 7:
                    padding_num = 9;
                    padding_value = 9;
                    break;
                case 8:
                    padding_num = 8;
                    padding_value = 8;
                    break;
                case 9:
                    padding_num = 7;
                    padding_value = 7;
                    break;
                case 10:
                    padding_num = 6;
                    padding_value = 6;
                    break;
                case 11:
                    padding_num = 5;
                    padding_value = 5;
                    break;
                case 12:
                    padding_num = 4;
                    padding_value = 4;
                    break;
                case 13:
                    padding_num = 3;
                    padding_value = 3;
                    break;
                case 14:
                    padding_num = 2;
                    padding_value = 2;
                    break;
                case 15:
                    padding_num = 1;
                    padding_value = 1;
                }
            }


            int plen_after_Padding = plen + padding_num;


            int i;
            for(i = 0; i < plen; ++i) {
                paddingResult[i] = p[i];
            }


            for(i = plen; i < plen_after_Padding; ++i) {
                paddingResult[i] = padding_value;
            }


            return paddingResult;
        }
    }
public static byte[] PKCS7Cutting(byte[] p, int plen) { int plen_after_cutting = 0;  if (plen != 0 && plen % 16 == 0) { if (p[plen - 1] == 1) {
            plen_after_cutting = plen - 1;  } else if (p[plen - 1] == 2) {
            plen_after_cutting = plen - 2;  } else if (p[plen - 1] == 3) {
            plen_after_cutting = plen - 3;  } else if (p[plen - 1] == 4) {
            plen_after_cutting = plen - 4;  } else if (p[plen - 1] == 5) {
            plen_after_cutting = plen - 5;  } else if (p[plen - 1] == 6) {
            plen_after_cutting = plen - 6;  } else if (p[plen - 1] == 7) {
            plen_after_cutting = plen - 7;  } else if (p[plen - 1] == 8) {
            plen_after_cutting = plen - 8;  } else if (p[plen - 1] == 9) {
            plen_after_cutting = plen - 9;  } else if (p[plen - 1] == 10) {
            plen_after_cutting = plen - 10;  } else if (p[plen - 1] == 11) {
            plen_after_cutting = plen - 11;  } else if (p[plen - 1] == 12) {
            plen_after_cutting = plen - 12;  } else if (p[plen - 1] == 13) {
            plen_after_cutting = plen - 13;  } else if (p[plen - 1] == 14) {
            plen_after_cutting = plen - 14;  } else if (p[plen - 1] == 15) {
            plen_after_cutting = plen - 15;  } else if (p[plen - 1] == 16) {
            plen_after_cutting = plen - 16;  } byte[] cuttingResult = new byte[plen_after_cutting];   for(int i = 0; i < plen_after_cutting; ++i) {
            cuttingResult[i] = p[i];  } return cuttingResult;  } else { return null;  }
}

}
