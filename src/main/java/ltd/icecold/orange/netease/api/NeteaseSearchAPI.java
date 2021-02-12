package ltd.icecold.orange.netease.api;

import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 网易云音乐搜索API
 *
 * @author ice-cold
 */
public class NeteaseSearchAPI {
    /**
     * 搜索
     * @param keywords 关键词
     * @param limit 返回数量
     * @param cookie 用户cookie
     * @return data
     */
    public NeteaseResponseBody search(String keywords, Integer limit, Map<String,String> cookie){
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/search/get", NeteaseCrypto.CryptoType.WEAPI, cookie , Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("s",keywords);
        data.put("type", "1");
        data.put("limit", limit+"");
        data.put("offset", "0");
        return NeteaseRequest.postRequest(requestOptions, data);
    }

    /**
     * 搜索
     * @param keywords 关键词
     * @param type 搜索类型  默认:1  1: 单曲, 10: 专辑, 100: 歌手, 1000: 歌单, 1002: 用户, 1004: MV, 1006: 歌词, 1009: 电台, 1014: 视频, 1018:综合
     * @param limit 返回数量
     * @param offset 偏移数量
     * @param cookie 用户cookie
     * @return json
     */
    public NeteaseResponseBody search(String keywords,Integer type,Integer limit,Integer offset, Map<String,String> cookie){
        NeteaseRequestOptions neteaseRequestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/search/get", NeteaseCrypto.CryptoType.WEAPI, cookie, Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("s",keywords);
        data.put("type", type+"");
        data.put("limit", limit+"");
        data.put("offset", offset+"");
        return NeteaseRequest.postRequest(neteaseRequestOptions, data);
    }

    public NeteaseResponseBody defaultKeyword(Map<String,String> cookie){
        NeteaseRequestOptions neteaseRequestOptions = new NeteaseRequestOptions("https://interface3.music.163.com/eapi/search/defaultkeyword/get", NeteaseCrypto.CryptoType.EAPI, cookie, Request.UserAgentType.ANDROID);
        Map<String, String> data = new HashMap<>();
        return NeteaseRequest.postRequest(neteaseRequestOptions, data);
    }
}
