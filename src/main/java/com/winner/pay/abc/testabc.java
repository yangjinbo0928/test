package com.winner.pay.abc;

public class testabc {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		curl -H "Content-type:application/json"
//		H "X-Frame-Options:DENY" -
//		H "Content-Security-Policy:SELF"'
//		X GET 
//		http://127.0.0.1:26003/jshkb/thoCouponList?
//		data=16de4312c34d02bd4 8a1faeOdfbf4839df41e2be2577665e9fb7c104b4bbb7db2b0a4b42835fd9cdb48f7ccc5ab8d493&mac=7AC201E45A8BF3A5264DEB26332ABBC1FEE8C063331728F2E42D37609785A9F1
	
		sm4  sm4=new sm4();
		sm3  sm3=new sm3();
		 String key = "DF78CFCE951680086E0C6E9CE2E2A0AA";
		 Boolean paddingFlag=true;
		 String plaintext="CustNo=1661525789085638&ThdID=DXJSC0001";
		String result = null;
		try {
			result = sm4.encryptEcbWithPadding(plaintext, key, paddingFlag);
			System.out.println("加密 || "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String  sm3MAC=sm3.sm3Sign(result);
		System.out.println("sm3MAC ||"+sm3MAC);
		String deResult=sm4.decryptEcbWithCutting(result, key, paddingFlag);
		System.out.println("解密 ||"+deResult);
		
	}

}
