package scms.common.util;



import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
 
import javax.crypto.Cipher;
 
public class TestRSA {
    public static void main(String[] args) {
        try {
            //怨듦컻�궎 諛� 媛쒖씤�궎 �깮�꽦
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic(); // 怨듦컻�궎
            Key privateKey = keyPair.getPrivate(); // 媛쒖씤�궎
            
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
 
            //System.out.println("public key modulus(" + publicKeySpec.getModulus() + ") exponent(" + publicKeySpec.getPublicExponent() + ")");
            //System.out.println("private key modulus(" + privateKeySpec.getModulus() + ") exponent(" + privateKeySpec.getPrivateExponent() + ")");
 
            
            // �븫�샇�솕(Encryption) �삁�젣
            String inputStr = "�꽭�씠�봽123"; // "�꽭�씠�봽123"�쓣 �븫�샇�솕�븳�떎!
 
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] arrCipherData = cipher.doFinal(inputStr.getBytes()); // �븫�샇�솕�맂 �뜲�씠�꽣(byte 諛곗뿴)
            String strCipher = new String(arrCipherData);
            
            System.out.println(strCipher); // �븫�샇�솕 寃곌낵臾� 異쒕젰(�늿�쑝濡� 蹂댁씠湲곗뿉�뒗 源⑥쭏 �닔 �엳�쓬)
 
            
            // 蹂듯샇�솕(Decryption) �삁�젣
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] arrData = cipher.doFinal(arrCipherData);
            String strResult = new String(arrData);
 
            System.out.println(strResult); // 蹂듯샇�솕 寃곌낵臾� 異쒕젰(�떎�떆 "�꽭�씠�봽123"�씠 異쒕젰�맖)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
