package ltd.icecold.orange.utils.crypto;

import ltd.icecold.orange.utils.Utils;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA Crypto
 *
 * @author ice-cold
 */
public class RSACrypto {
    /**
     * RSA NoPadding加密
     *
     * @param originalContent 原始数据
     * @param encryptKey      秘钥
     * @return 加密数据
     */
    public static byte[] encryptNoPadding(byte[] originalContent, String encryptKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding", "BC");
            PublicKey pubKey = getPublicKey(encryptKey, 65537);
            //SecretKeySpec skeySpec = new SecretKeySpec(encryptKey, "RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(originalContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static PublicKey getPublicKey(String modulus, int publicExponent) {
        BigInteger bigIntegerModulus = new BigInteger(modulus, 16);
        RSAPublicKeySpec rsaSpec = new RSAPublicKeySpec(bigIntegerModulus,
                BigInteger.valueOf(publicExponent));
        PublicKey pKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            pKey = kf.generatePublic(rsaSpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return pKey;
    }

    public static String rsaEncrypt(String text, String pubKey, String modulus) {

        text = new StringBuffer(text).reverse().toString();

        BigInteger biText = new BigInteger(Utils.bytesToHexString(text.getBytes(StandardCharsets.UTF_8)), 16);
        BigInteger biEx = new BigInteger(pubKey, 16);
        BigInteger biMod = new BigInteger(modulus, 16);
        BigInteger biRet = biText.modPow(biEx, biMod);

        return zFill(biRet.toString(16), 256);

    }

    /**
     * 长度不够前面补充0
     *
     * @param str
     * @param size
     * @return
     */
    private static String zFill(String str, int size) {
        while (str.length() < size) {
            str = "0" + str;
        }
        return str;
    }
}
