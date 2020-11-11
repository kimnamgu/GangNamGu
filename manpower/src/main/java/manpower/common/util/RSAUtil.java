package manpower.common.util;
 
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
 
import javax.crypto.Cipher;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 
/** Client -> Server �뜲�씠�꽣 �쟾�넚媛� �븫�샇�솕 湲곕뒫�쓣 �떞�떦 **/
public class RSAUtil {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
 
    private KeyPairGenerator generator;
    private KeyFactory keyFactory;
    private KeyPair keyPair;
    private Cipher cipher;
 
    public RSAUtil() {
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            keyFactory = KeyFactory.getInstance("RSA");
            cipher = Cipher.getInstance("RSA");
        } catch (Exception e) {
            logger.warn("RSAUtil �깮�꽦 �떎�뙣.", e);
        }
    }
 
    /** �깉濡쒖슫 �궎媛믪쓣 媛�吏� RSA �깮�꽦
     *  @return vo.other.RSA **/
    public RSA createRSA() {
        RSA rsa = null;
        try {
            keyPair = generator.generateKeyPair();
 
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
 
            RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String modulus = publicSpec.getModulus().toString(16);
            String exponent = publicSpec.getPublicExponent().toString(16);
            rsa = new RSA(privateKey, modulus, exponent);
        } catch (Exception e) {
            logger.warn("RSAUtil.createRSA()", e);
        }
        return rsa;
    }
 
    /** 媛쒖씤�궎瑜� �씠�슜�븳 RSA 蹂듯샇�솕
     *  @param privateKey session�뿉 ���옣�맂 PrivateKey
     *  @param encryptedText �븫�샇�솕�맂 臾몄옄�뿴 **/
    public String getDecryptText(PrivateKey privateKey, String encryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(hexToByteArray(encryptedText));
        return new String(decryptedBytes, "UTF-8");
    }
 
    // 16吏꾩닔 臾몄옄�뿴�쓣 byte 諛곗뿴濡� 蹂��솚
    private byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[] {};
        }
 
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
 
}