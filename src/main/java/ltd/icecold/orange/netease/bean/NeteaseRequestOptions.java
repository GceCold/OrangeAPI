package ltd.icecold.orange.netease.bean;


import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.network.Request;

import java.util.Map;

/**
 * Request Data
 *
 * @author ice-cold
 */
public class NeteaseRequestOptions {
    /**
     * api地址
     */
    private String url;
    /**
     * 加密方式
     */
    private NeteaseCrypto.CryptoType crypto;
    /**
     * 用户cookie
     */
    private Map<String,String> cookie;
    /**
     * UserAgent
     */
    private String userAgent;

    public NeteaseRequestOptions(String url, NeteaseCrypto.CryptoType crypto, Map<String,String> cookie, Request.UserAgentType userAgent) {
        this.url = url;
        this.crypto = crypto;
        this.cookie = cookie;
        this.userAgent = Request.getUserAgent(userAgent);
    }


    public String getUrl() {
        return url;
    }

    public NeteaseCrypto.CryptoType getCrypto() {
        return crypto;
    }

    public Map<String,String> getCookie() {
        return cookie;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
