package ltd.icecold.orange.netease.bean;

import java.util.Map;

/**
 * Cookie and Body
 *
 * @author ice-cold
 */
public class NeteaseResponseBody {
    /**
     * 访问返回Cookie
     */
    private Map<String,String> cookie;
    /**
     * 返回数据主体
     */
    private String body;
    /**
     * 访问响应码，并非数据主体中的code码，两者不同
     */
    private int code;

    public NeteaseResponseBody(Map<String, String> cookie, String body, int code) {
        this.cookie = cookie;
        this.body = body;
        this.code = code;
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public String getBody() {
        return body;
    }

    public int getCode() {
        return code;
    }

    public NeteaseResponseBody(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "NeteaseResponseBody{" +
                "cookie=" + cookie +
                ", body='" + body + '\'' +
                ", code=" + code +
                '}';
    }
}
