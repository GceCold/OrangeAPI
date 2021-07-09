package ltd.icecold.orange.tencent;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.icecold.orange.network.Request;
import ltd.icecold.orange.network.UserAgent;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TencentMusicApi {
    public static String search(String key,Integer limit) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", UserAgent.WINDOWS10_CHROME.getUserAgent());
        headers.put("X-Real-IP", Request.getRandomChinaIp());
        String url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?format=json&p=1&n="+limit+"&w="+key+"&aggr=1&lossless=1&cr=1&new_json=1";
        Connection.Response response = Request.sendGet(url, headers, new HashMap<>());
        return response.body();
    }

    public static String url(String id) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", UserAgent.WINDOWS10_CHROME.getUserAgent());
        headers.put("X-Real-IP", Request.getRandomChinaIp());
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_play_single_song.fcg?songmid="+id+"&platform=yqq&format=json&br=320";
        Connection.Response response = Request.sendGet(url, headers, new HashMap<>());
        JsonObject data = new JsonParser().parse(response.body()).getAsJsonObject();
        return "";
    }
}
