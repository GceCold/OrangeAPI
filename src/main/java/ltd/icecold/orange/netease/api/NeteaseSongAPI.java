package ltd.icecold.orange.netease.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;
import ltd.icecold.orange.network.UserAgent;
import ltd.icecold.orange.utils.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易云音乐歌曲相关API
 *
 * @author ice-cold
 */
public class NeteaseSongAPI {



    /**
     * 获取音乐URL
     *
     * @param id     音乐ID 可传入多个音乐ID 用,分隔
     * @param br     音乐码率
     * @param cookie 用户cookie
     * @return result
     */
    public static NeteaseResponseBody musicUrl(String id, String br, Map<String, String> cookie) {
        if (!cookie.containsKey("MUSIC_U")) {
            cookie.put("_ntes_nuid", DigestUtils.md5Hex(Utils.getRandomString(16)));
        }
        cookie.put("os", "pc");
        Map<String, String> data = new HashMap<>();
        data.put("ids", "[" + id + "]");
        data.put("br", br);
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/api/song/enhance/player/url", NeteaseCrypto.CryptoType.LINUXAPI, cookie, Request.UserAgentType.PC);
        return NeteaseRequest.postRequest(requestOptions, data);
    }

    /**
     * 获取歌曲歌词
     * @param id 歌曲id
     * @return result
     */
    public static NeteaseResponseBody lyric(String id) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://music.163.com");
        headers.put("User-Agent", UserAgent.WINDOWS10_FIREFOX.getUserAgent());
        headers.put("X-Real-IP", Request.getRandomChinaIp());
        try {
            Connection.Response response = Request.sendGet("https://music.163.com/api/song/lyric?id=" + id + "&lv=-1&kv=-1&tv=-1", headers, new HashMap<>());
            return new NeteaseResponseBody(response.cookies(), response.body(), response.statusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查音乐可用性
     *
     * @param id     音乐ID 可传入多个音乐ID 用,分隔
     * @param cookie cookie
     * @return 是否可用 true可用 false没版权
     */
    public static boolean checkMusic(String id, Map<String, String> cookie) {
        return checkMusic(id, "999000", cookie);
    }

    /**
     * 检查音乐可用性
     *
     * @param id     音乐ID 可传入多个音乐ID 用,分隔
     * @param br     音乐码率 默认999000
     * @param cookie cookie
     * @return 是否可用 true可用 false没版权
     */
    public static boolean checkMusic(String id, String br, Map<String, String> cookie) {
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/song/enhance/player/url", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("ids", "[" + id + "]");
        data.put("br", br);
        JsonObject asJsonObject = new JsonParser().parse(NeteaseRequest.postRequest(requestOptions, data).getBody()).getAsJsonObject();
        if (asJsonObject.get("code").getAsInt() == 200) {
            return asJsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("code").getAsInt() == 200;
        } else {
            return false;
        }
    }
}
