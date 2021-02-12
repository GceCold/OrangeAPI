package ltd.icecold.orange.utils.crypto;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Hmac-SHA1 Crypto
 *
 * @author ice-cold
 */
public class HmacSHA1 {
    /**
     * HMAC-SHA1
     *
     * @param originalContent 被签名的字符串
     * @param encryptKey      密钥
     * @return HMAC-SHA1加密数据
     */
    public static byte[] hmacSHA1Encrypt(String originalContent, String encryptKey) {
        try {
            byte[] data = encryptKey.getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] text = originalContent.getBytes(StandardCharsets.UTF_8);
            return mac.doFinal(text);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
