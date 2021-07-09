package ltd.icecold.orange.netease.api;

import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易云音乐用户相关API
 *
 * @author ice-cold
 */
public class NeteaseUserAPI {
    private Map<String, String> cookie = new HashMap<>();
    public String userDetail;

    public Map<String, String> getCookie() {
        return cookie;
    }

    /**
     * 邮箱登录
     * 如果body中的code为200则登录成功 cookie可正常使用 502为账号或密码错误
     *
     * @param userName 邮箱
     * @param password 密码需MD5加密
     * @return data
     */
    public NeteaseResponseBody login(String userName, String password) {
        cookie.put("os", "pc");
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        System.out.println(requestOptions.getUserAgent());
        Map<String, String> data = new HashMap<>();

        data.put("username", userName);
        data.put("password", password);
        data.put("rememberLogin", "true");
        //data.put("csrf_token", cookie.get("__csrf"));

        NeteaseResponseBody responseBody = NeteaseRequest.postRequest(requestOptions, data);
        cookie = responseBody.getCookie();
        userDetail = responseBody.getBody();
        return responseBody;
    }

    /**
     * 手机登录
     * 如果body中的code为200则登录成功 cookie可正常使用 502为账号或密码错误
     *
     * @param phone    邮箱
     * @param password 密码需MD5加密
     * @return data
     */
    public NeteaseResponseBody loginPhone(String phone, String password) {
        cookie.put("os", "pc");
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/cellphone", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();

        data.put("phone", phone);
        data.put("password", password);
        data.put("rememberLogin", "true");
        //data.put("csrf_token", cookie.get("__csrf"));

        NeteaseResponseBody responseBody = NeteaseRequest.postRequest(requestOptions, data);
        cookie = responseBody.getCookie();
        userDetail = responseBody.getBody();
        return responseBody;
    }

    /**
     * 刷新登录
     * 根据body中的code判断是否刷新成功
     *
     * @return data
     */
    public NeteaseResponseBody loginRefresh() {
        if (cookie.isEmpty()) {
            throw new NullPointerException("Not logged in yet");
        }
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/token/refresh", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("csrf_token", cookie.get("__csrf"));
        return NeteaseRequest.postRequest(requestOptions, data);
    }

    /**
     * 生成二维码key
     * @return key
     */
    private static NeteaseResponseBody getQrKey() {
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/qrcode/unikey", NeteaseCrypto.CryptoType.WEAPI, new HashMap<>(), Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("type", "1");
        return NeteaseRequest.postRequest(requestOptions, data);
    }

    /**
     * 生成登录二维码
     * @param key 二维码key
     * @param width 二维码图片宽
     * @param height 二维码图片搞
     * @return 二维码
     * @throws WriterException
     */
    public static BufferedImage generateQRCode(String key,int width,int height) throws WriterException {
        String qrUrl = "https://music.163.com/login?codekey=" + key;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrUrl, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        return MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
    }

    /**
     * 检测二维码扫描状态
     * @param key 二维码key
     * @return 800 二维码不存在或已过期；801 等待扫码；802 授权中；803 授权登录成功
     */
    public static NeteaseResponseBody checkQrLogin(String key) {
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/qrcode/client/login", NeteaseCrypto.CryptoType.WEAPI, new HashMap<>(), Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("type", "1");
        data.put("key", key);
        return NeteaseRequest.postRequest(requestOptions, data);
    }
}
