package ltd.icecold.orange.utils.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;


/**
 * Aes Crypto Class
 *
 * @author ice-cold
 */
public class AESCrypto {
    public static boolean initialized = false;

    /**
     * aes-128-ecb加密
     * @param originalContent 原始数据
     * @param encryptKey 秘钥
     * @return 加密数据
     */
    public byte[] encryptECB(byte[] originalContent, byte[] encryptKey) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(originalContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes-128-ecb解密
     * @param content 密文
     * @param aesKey 密钥
     * @param ivByte iv
     * @return 解密数据
     */
    public byte[] decryptECB(byte[] content, byte[] aesKey, byte[] ivByte) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key sKeySpec = new SecretKeySpec(aesKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIv(ivByte));
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * aes-128-cbc加密
     * @param originalContent 原始数据
     * @param encryptKey 秘钥
     * @param ivByte iv
     * @return 加密数据
     */
    public byte[] encryptCBC(byte[] originalContent, byte[] encryptKey, byte[] ivByte) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(ivByte));
            return cipher.doFinal(originalContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes-128-cbc解密
     * @param content 密文
     * @param aesKey 密钥
     * @param ivByte iv
     * @return 解密数据
     */
    public byte[] decryptCBC(byte[] content, byte[] aesKey, byte[] ivByte) {
        initialize();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key sKeySpec = new SecretKeySpec(aesKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIv(ivByte));
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void initialize() {
        if (initialized) {
            return;
        }
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    /**
     * 生成iv
     * @param iv iv
     * @return params
     * @throws Exception Exception
     */
    private static AlgorithmParameters generateIv(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
}
