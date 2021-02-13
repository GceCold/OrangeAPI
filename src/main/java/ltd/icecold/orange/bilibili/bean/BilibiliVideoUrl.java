package ltd.icecold.orange.bilibili.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ice-cold
 */
public class BilibiliVideoUrl {
    private String refererUrl;
    private List<String> videoUrlList;

    /**
     * Bilibili视频链接
     * @param refererUrl 下载时需要的referer地址
     * @param videoUrlList 视频地址
     */
    public BilibiliVideoUrl(String refererUrl, List<String> videoUrlList) {
        this.refererUrl = refererUrl;
        this.videoUrlList = videoUrlList;
    }

    public String getRefererUrl() {
        return refererUrl;
    }

    public List<String> getVideoUrlList() {
        return videoUrlList;
    }
}
