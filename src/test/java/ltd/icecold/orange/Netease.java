package ltd.icecold.orange;

import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.network.Request;

import java.util.HashMap;
import java.util.Map;

public class Netease {
    public static void main(String[] args) {

        //NeteaseUser neteaseUser = new NeteaseUser();
        //System.out.println(neteaseUser.loginPhone("13366271172", "656f88bf058217472a09db974dbdb597").getCookie());

        NeteaseRequestOptions neteaseRequestOptions = new NeteaseRequestOptions("https://interface3.music.163.com/eapi/search/defaultkeyword/get", NeteaseCrypto.CryptoType.EAPI, new HashMap<>(), Request.UserAgentType.ANDROID);
        Map<String, String> data = new HashMap<>();
        System.out.println(NeteaseRequest.postRequest(neteaseRequestOptions, data).getBody());

/**
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/api/song/enhance/player/url", NeteaseCrypto.CryptoType.LINUXAPI,new HashMap<>(), Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("ids","["+461332138+"]");
        data.put("br", "999999");
        System.out.println(NeteaseRequest.postRequest(requestOptions, data).getBody());

        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/v3/song/detail", NeteaseCrypto.CryptoType.WEAPI,new HashMap<>(), Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("c","[{\"id\":"+461332138+"}]");
        data.put("ids", "461332138");
        System.out.println(Utils.toPrettyFormat(NeteaseRequest.postRequest(requestOptions, data).getBody()));
 */
    }
}
