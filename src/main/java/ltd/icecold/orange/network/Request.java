package ltd.icecold.orange.network;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Send Request To Music Server
 *
 * @author ice-cold
 */
public class Request {
    public static List<String> chinaIp = new ArrayList<>();
    static {
        chinaIp.add("103.245.52");
        chinaIp.add("103.245.60");
        chinaIp.add("103.245.80");
        chinaIp.add("103.245.124");
        chinaIp.add("103.245.128");
        chinaIp.add("103.247.212");
        chinaIp.add("121.46.128");
        chinaIp.add("122.10.168");
        chinaIp.add("144.48.156");
        chinaIp.add("157.119.132");
    }

    /**
     * 获取随机中国大陆ip
     * @return ip
     */
    public static String getRandomChinaIp(){
        Random random =new Random();
        return chinaIp.get(random.nextInt(10)) + "." + (random.nextInt(254) + 1);
    }

    /**
     * 根据UserAgent类型获取UserAgent
     * @param type 类型
     * @return UserAgent
     */
    public static String getUserAgent(UserAgentType type){
        Random random =new Random();
        if (type == UserAgentType.ANDROID){
            return UserAgent.getUserAgent(random.nextInt(3)+7);
        } else
        if (type == UserAgentType.IPHONE){
            return UserAgent.getUserAgent(random.nextInt(6)+1);
        }else
        if (type == UserAgentType.PC){
            return UserAgent.getUserAgent(random.nextInt(8)+9);
        }else {
            throw new NullPointerException("Unknown UserAgent Type");
        }
    }

    public enum UserAgentType{
        PC,IPHONE,ANDROID
    }

    public static Connection.Response sendPost(String url, Map<String,String> data) throws IOException {
        return Jsoup
                .connect(url)
                .data(data)
                .timeout(5 * 1000)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute();
    }

    public static Connection.Response sendPost(String url, Map<String,String> data,Map<String,String> header,Map<String,String> cookie) throws IOException {
         return Jsoup
                .connect(url)
                .headers(header)
                .cookies(cookie)
                .data(data)
                .timeout(5 * 1000)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute();
    }
    public static Connection.Response sendGet(String url,Map<String, String> headers,Map<String, String> cookies) throws IOException {
        return Jsoup
                .connect(url)
                .headers(headers)
                .cookies(cookies)
                .timeout(5*1000)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
    }
}
