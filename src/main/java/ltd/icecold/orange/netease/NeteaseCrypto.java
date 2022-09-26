package ltd.icecold.orange.netease;

import com.google.gson.Gson;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.utils.Utils;
import ltd.icecold.orange.utils.crypto.AESCrypto;
import ltd.icecold.orange.utils.crypto.RSACrypto;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易云数据处理
 *
 * @author ice-cold
 */
public class NeteaseCrypto {
    private static final byte[] presetKey = "0CoJUm6Qyw8W8jud".getBytes(StandardCharsets.UTF_8);
    private static final byte[] linuxApiKey = "rFgB&h#%2?^eDg:Q".getBytes(StandardCharsets.UTF_8);
    private static final byte[] iv = "0102030405060708".getBytes(StandardCharsets.UTF_8);
    private static final byte[] eapiKey = "e82ckenh8dichen8".getBytes(StandardCharsets.UTF_8);
    private static final String pubKey = "010001";
    private static final String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";


    public static Map<String,String> linuxApiCrypto(Map<String,Object> requestData){
        String dataJson = new Gson().toJson(requestData);
        AESCrypto aesCrypto = new AESCrypto();
        Map<String, String> data= new HashMap<>();
        data.put("eparams", Utils.bytesToHexString(
                aesCrypto.encryptECB(
                        dataJson.getBytes(StandardCharsets.UTF_8),linuxApiKey)
        ).toUpperCase());
        return data;
    }


    public static Map<String,String> weApiCrypto(Map<String,String> requestData){
        String dataJson = new Gson().toJson(requestData);
        String tempKey = Utils.getRandomString(16);

        AESCrypto aesCrypto = new AESCrypto();

        String params = Base64.encodeBase64String(
                aesCrypto.encryptCBC(
                        Base64.encodeBase64(
                                aesCrypto.encryptCBC(
                                        dataJson.getBytes(StandardCharsets.UTF_8),
                                        presetKey,
                                        iv
                                )
                        )
                        , tempKey.getBytes(StandardCharsets.UTF_8), iv)
        );

        String encSecKey = RSACrypto.rsaEncrypt(tempKey, pubKey, modulus);

        Map<String, String> data = new HashMap<>();
        data.put("key",tempKey);
        data.put("params",params);
        data.put("encSecKey",encSecKey);
        return data;
    }

    public static Map<String,String> eapiCrypto(Map<String, Object> requestData, String url){
        String dataJson = new Gson().toJson(requestData);
        String message = DigestUtils.md5Hex("nobody"+url+"use"+dataJson+"md5forencrypt");
        String data = url+"-36cd479b6b5-"+dataJson+"-36cd479b6b5-"+message;
        HashMap<String,String> params = new HashMap<>();
        AESCrypto aesCrypto = new AESCrypto();
        params.put("params",Utils.bytesToHexString(aesCrypto.encryptECB(data.getBytes(StandardCharsets.UTF_8),eapiKey)).toUpperCase());
        return params;
    }

    public enum CryptoType{
        WEAPI,LINUXAPI,EAPI,API
    }
}
