package ltd.icecold.orange.netease;

import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;
import ltd.icecold.orange.network.UserAgent;
import ltd.icecold.orange.utils.RemoveNullKeyValueUtils;
import ltd.icecold.orange.utils.Utils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 发送请求
 *
 * @author ice-cold
 */
public class NeteaseRequest {
    /**
     * 发送post请求
     * @param requestOptions 请求相关设置
     * @param requestData 请求数据
     * @return 相应数据
     */
    public static NeteaseResponseBody postRequest(NeteaseRequestOptions requestOptions, Map<String, String> requestData) {
        switch (requestOptions.getCrypto()){
            case LINUXAPI:
            case API:
                return linuxPostRequest(requestOptions, requestData);
            case WEAPI:
                return weapiPostRequest(requestOptions, requestData);
            case EAPI:
                return eapiPostRequest(requestOptions, requestData);
            default:
                throw new NullPointerException("Error Crypto Type");
        }
    }

    @Deprecated
    private static NeteaseResponseBody apiPostRequest(NeteaseRequestOptions requestOptions, Map<String, String> requestData) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            if (requestOptions.getUrl().contains("music.163.com")) {
                headers.put("Referer", "https://music.163.com");
            }
            headers.put("User-Agent", requestOptions.getUserAgent());
            headers.put("X-Real-IP", Request.getRandomChinaIp());

            Connection.Response execute = Jsoup
                    .connect(requestOptions.getUrl().replaceAll("\\w*api", "weapi"))
                    .headers(headers)
                    .cookies(requestOptions.getCookie())
                    .data(requestData)
                    .timeout(10 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();
            return new NeteaseResponseBody(execute.cookies(), execute.body(), execute.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NeteaseResponseBody weapiPostRequest(NeteaseRequestOptions requestOptions, Map<String, String> requestData) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            if (requestOptions.getUrl().contains("music.163.com")) {
                headers.put("Referer", "https://music.163.com");
            }
            headers.put("User-Agent", requestOptions.getUserAgent());
            headers.put("X-Real-IP", Request.getRandomChinaIp());
            Map<String, String> cookie = requestOptions.getCookie();
            Map<String, String> data = NeteaseCrypto.weApiCrypto(requestData);
            cookie.put("__remember_me","true");
            cookie.put("NMTID",data.get("key"));
            data.remove("key");
            Connection.Response execute = Jsoup
                    .connect(requestOptions.getUrl().replaceAll("\\w*api", "weapi"))
                    .headers(headers)
                    .cookies(cookie)
                    .data(data)
                    .timeout(10 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();

            return new NeteaseResponseBody(execute.cookies(), execute.body(), execute.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NeteaseResponseBody linuxPostRequest(NeteaseRequestOptions requestOptions, Map<String, String> requestData) {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            if (requestOptions.getUrl().contains("music.163.com")) {
                headers.put("Referer", "https://music.163.com");
            }
            headers.put("User-Agent", UserAgent.LINUX.getUserAgent());
            headers.put("X-Real-IP", Request.getRandomChinaIp());
            requestData.put("csrf_token", requestOptions.getCookie().getOrDefault("__csrf", ""));

            Map<String, Object> data = new HashMap<>();
            data.put("method", "POST");
            data.put("url", requestOptions.getUrl());
            data.put("params", requestData);

            Map<String, String> stringStringMap = NeteaseCrypto.linuxApiCrypto(data);

            Connection.Response execute = Jsoup
                    .connect("https://music.163.com/api/linux/forward")
                    .headers(headers)
                    .cookies(requestOptions.getCookie())
                    .data(stringStringMap)
                    .timeout(10 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();

            return new NeteaseResponseBody(execute.cookies(), execute.body(), execute.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static NeteaseResponseBody eapiPostRequest(NeteaseRequestOptions requestOptions, Map<String, String> requestData) {
        Map<String, String> cookie = requestOptions.getCookie();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        if (requestOptions.getUrl().contains("music.163.com")) {
            headers.put("Referer", "https://music.163.com");
        }
        headers.put("User-Agent", requestOptions.getUserAgent());
        headers.put("X-Real-IP", Request.getRandomChinaIp());

        if (cookie.containsKey("MUSIC_U")) headers.put("MUSIC_U", cookie.get("MUSIC_U"));
        if (cookie.containsKey("MUSIC_A")) headers.put("MUSIC_A", cookie.get("MUSIC_A"));


        Map<String, String> headerData = new HashMap<>();
        headerData.put("osver", cookie.get("osver"));
        headerData.put("deviceId", cookie.get("deviceId"));
        headerData.put("appver", cookie.getOrDefault("appver", "8.0.0"));
        headerData.put("versioncode", cookie.getOrDefault("versioncode", "140"));
        headerData.put("mobilename", cookie.get("mobilename"));
        headerData.put("buildver", cookie.getOrDefault("buildver", String.valueOf(System.currentTimeMillis()).substring(0, 10)));
        headerData.put("resolution", cookie.getOrDefault("resolution", "1920x1080"));
        headerData.put("os", cookie.getOrDefault("os", "android"));
        headerData.put("channel", cookie.get("channel"));
        Random random = new Random();
        headerData.put("requestId", System.currentTimeMillis() + "_" + Utils.padStart(4, random.nextInt(1000)));
        RemoveNullKeyValueUtils.removeNullValue(headerData);
        headerData.put("__csrf", cookie.getOrDefault("__csrf", ""));
        Map<String, Object> requestData1 = new HashMap<>(requestData);
        requestData1.put("head", headerData);
        Map<String, String> eapiCryptoData = NeteaseCrypto.eapiCrypto(requestData1, "/api/search/defaultkeyword/get");

        headers.put("Cookie", Utils.cookieMap2String(headerData));
        try {
            Connection.Response execute = Jsoup
                    .connect(requestOptions.getUrl().replaceAll("\\w*api", "weapi"))
                    .headers(headers)
                    .cookies(headerData)
                    .data(eapiCryptoData)
                    .timeout(10 * 1000)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();

            return new NeteaseResponseBody(execute.cookies(), execute.body(), execute.statusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
