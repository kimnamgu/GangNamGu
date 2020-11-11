package nexti.ejms.util.crypto;

import nexti.ejms.util.Base64;

/**
 * SeedTest
 * 작성일: 2008.4.22
 * 작성자: 장덕용 
 * 사용방법
 * 	1. 암호화: ecrypt -> base64 encoding
 *  2. 복호화: base64 decoding -> decrypt
 */

public class SeedTest {

	public static void main(String[] args) throws Exception {
		String text = "1111112111111";
		String key = "1234567890123456";	// 16byte(128bit)
		StringBuffer trace = new StringBuffer();
		
		trace.append("Plain Text: [").append(text).append("]");
		System.out.println(trace.toString());
		
		SeedCipher seed = new SeedCipher();
		byte[] encryptTextbyte = seed.encrypt(text, key.getBytes(), "UTF-8");
		String encryptTextBase64 = Base64.encodeBytes(encryptTextbyte);
		
		trace = new StringBuffer();
		trace.append("Encrypt Text (Base64 Encoding): [").append(encryptTextBase64).append("]");
		System.out.println(trace.toString());
		
		byte[] encryptbytes = Base64.decode(encryptTextBase64);
		String decryptText = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
		
		trace = new StringBuffer();
		trace.append("Decrypt Text: [").append(decryptText).append("]");
		System.out.println(trace.toString());
	}
}
