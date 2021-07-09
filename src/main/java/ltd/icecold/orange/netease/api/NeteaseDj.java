package ltd.icecold.orange.netease.api;

import ltd.icecold.orange.netease.NeteaseCrypto;
import ltd.icecold.orange.netease.NeteaseRequest;
import ltd.icecold.orange.netease.bean.NeteaseRequestOptions;
import ltd.icecold.orange.netease.bean.NeteaseResponseBody;
import ltd.icecold.orange.network.Request;

import java.util.HashMap;
import java.util.Map;

public class NeteaseDj {
    public static NeteaseResponseBody djProgram(String rid,Map<String,String> cookie){
        return djProgram(rid,30,0,false,cookie);
    }
    public static NeteaseResponseBody djProgram(String rid,Integer limit,Integer offset,Boolean asc,Map<String,String> cookie){
        NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/weapi/dj/program/byradio", NeteaseCrypto.CryptoType.WEAPI, cookie , Request.UserAgentType.PC);
        Map<String, String> data = new HashMap<>();
        data.put("radioId",rid);
        data.put("offset", offset+"");
        data.put("limit", limit+"");
        data.put("asc", String.valueOf(asc));
        return NeteaseRequest.postRequest(requestOptions, data);
    }
}
