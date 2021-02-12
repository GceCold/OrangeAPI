package ltd.icecold.orange.netease.api;

import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * 网易云音乐歌单相关API
 *
 * @author ice-cold
 */
public class NeteasePlayListAPI {

    /**
     * 获取歌单详情 传入歌单id获取对应歌单内的所有的音乐(未登录状态只能获取不完整的歌单,登录后是完整的)
     * @param id 歌单 id
     * @param s 歌单最近的 s 个收藏者
     * @return result
     */
    public static NeteaseResponseBody playlistDetail(String id,String s,Map<String,String> cookie){
        Map<String, String> data = new HashMap<>();
        data.put("id", id);
        data.put("n", "100000");
        data.put("s", s);
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/api/v6/playlist/detail", NeteaseCrypto.CryptoType.LINUXAPI, cookie, Request.UserAgentType.PC);
        return NeteaseRequest.postRequest(requestOptions, data);
    }

}
