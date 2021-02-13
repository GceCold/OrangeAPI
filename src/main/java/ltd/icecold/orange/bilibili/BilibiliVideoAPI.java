package ltd.icecold.orange.bilibili;

import com.google.gson.*;
import ltd.icecold.orange.bilibili.bean.BilibiliVideoUrl;
import ltd.icecold.orange.network.Request;
import ltd.icecold.orange.network.UserAgent;
import ltd.icecold.orange.utils.BilibiliDownloaderThread;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bilibili Video Api
 *
 * @author ice-cold
 */
public class BilibiliVideoAPI {

    private BilibiliVideoUrl bilibiliVideoUrl;

    /**
     * bv号转av号
     * @param bid bv号
     * @return av号
     * @throws IOException Exception
     */
    public static String bv2Av(String bid) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", UserAgent.WINDOWS10_CHROME.getUserAgent());
        Connection.Response response = Request.sendGet("http://api.bilibili.com/x/web-interface/archive/stat?bvid="+bid, headers, new HashMap<>());
        String body = response.body();
        if (new JsonParser().parse(body).getAsJsonObject().get("code").getAsInt() != 0){
            return null;
        }
        return new JsonParser().parse(body).getAsJsonObject().get("data").getAsJsonObject().get("aid").getAsString();
    }

    /**
     * 获取视频cid
     * @param aid aid
     * @return cid
     * @throws IOException Exception
     */
    public static String getCid(String aid) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent",UserAgent.WINDOWS10_CHROME.getUserAgent());
        Connection.Response response = Request.sendGet("https://api.bilibili.com/x/web-interface/view?aid="+aid, headers, new HashMap<>());
        String body = response.body();
        if (new JsonParser().parse(body).getAsJsonObject().get("code").getAsInt() != 0){
            return null;
        }
        return new JsonParser().parse(body).getAsJsonObject().get("data").getAsJsonObject().get("cid").getAsString();
    }

    /**
     * 获取视频链接 免登陆1080p（无法直接访问下载） 可能会有多个文件
     * @param bid 视频bid(bvxxxxx)
     * @return this
     * @throws IOException Exception
     */
    public BilibiliVideoAPI getVideoUrlV1(String bid) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent",UserAgent.WINDOWS10_CHROME.getUserAgent());
        String aid = bv2Av(bid);
        String cid = getCid(aid);
        System.out.println();
        String params = "appkey=iVGUTjsxvpLeuDCf&cid="+cid+"&otype=json&qn=112&quality=112&type=";
        String sign = DigestUtils.md5Hex(params+"aHRmhWMLkdeMuILqORnYZocwMBpMEOdt");
        String apiUrl = "https://interface.bilibili.com/v2/playurl?"+params+"&sign="+sign;
        headers.put("Referer",apiUrl);
        Connection.Response response = Request.sendGet(apiUrl, headers, new HashMap<>());
        String body = response.body();
        JsonArray durl = new JsonParser().parse(body).getAsJsonObject().get("durl").getAsJsonArray();
        List<String> videoUrlList = new ArrayList<>();
        for (JsonElement videoUrl:durl){
            videoUrlList.add(videoUrl.getAsJsonObject().get("url").getAsString());
        }
        bilibiliVideoUrl = new BilibiliVideoUrl(apiUrl,videoUrlList);
        return this;
    }

    public BilibiliVideoUrl getVideoUrlList(){return bilibiliVideoUrl;}


    /**
     * 下载视频
     * @param targetFile 目标文件
     * @param threadNum 线程数
     * @return 所有视频文件的BilibiliDownloaderThread
     * @throws Exception Exception
     */
    public List<BilibiliDownloaderThread> download(File targetFile, int threadNum) throws Exception {
        if (bilibiliVideoUrl.getVideoUrlList().size() == 0){
            throw new NullPointerException("Null Video Info");
        }
        List<BilibiliDownloaderThread> download = new ArrayList<>();
        for (String url:bilibiliVideoUrl.getVideoUrlList()){
            BilibiliDownloaderThread bilibiliDownloaderThread = new BilibiliDownloaderThread(url,targetFile, threadNum,bilibiliVideoUrl.getRefererUrl());
            bilibiliDownloaderThread.start();
            download.add(bilibiliDownloaderThread);
        }
        return download;
    }

}
