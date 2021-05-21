package ltd.icecold.orange.netease.api;

import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 网易云音乐用户相关API
 *
 * @author ice-cold
 */
public class NeteaseUserAPI {
    private Map<String,String> cookie = new HashMap<>();
    public String userDetail;
    public Map<String, String> getCookie() {
        return cookie;
    }

    /**
     * 邮箱登录
     * 如果body中的code为200则登录成功 cookie可正常使用 502为账号或密码错误
     * @param userName 邮箱
     * @param password 密码需MD5加密
     * @return data
     */
    public NeteaseResponseBody login(String userName, String password){
        cookie.put("os","pc");
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login",  NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        System.out.println(requestOptions.getUserAgent());
        Map<String, String> data = new HashMap<>();

        data.put("username",userName);
        data.put("password", password);
        data.put("rememberLogin","true");
        //data.put("csrf_token", cookie.get("__csrf"));

        NeteaseResponseBody responseBody = NeteaseRequest.postRequest(requestOptions, data);
        cookie = responseBody.getCookie();
        userDetail = responseBody.getBody();
        return responseBody;
    }

    /**
     * 手机登录
     * 如果body中的code为200则登录成功 cookie可正常使用 502为账号或密码错误
     * @param phone 邮箱
     * @param password 密码需MD5加密
     * @return data
     */
    public NeteaseResponseBody loginPhone(String phone,String password){
        cookie.put("os","pc");
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/cellphone",  NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();

        data.put("phone",phone);
        data.put("password", password);
        data.put("rememberLogin","true");
        //data.put("csrf_token", cookie.get("__csrf"));

        NeteaseResponseBody responseBody = NeteaseRequest.postRequest(requestOptions, data);
        cookie = responseBody.getCookie();
        userDetail = responseBody.getBody();
        return responseBody;
    }

    /**
     * 刷新登录
     * 根据body中的code判断是否刷新成功
     * @return data
     */
    public NeteaseResponseBody loginRefresh(){
        if (cookie.isEmpty()){
            throw new NullPointerException("Not logged in yet");
        }
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/login/token/refresh", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("csrf_token", cookie.get("__csrf"));
        return NeteaseRequest.postRequest(requestOptions, data);
    }
    
}
