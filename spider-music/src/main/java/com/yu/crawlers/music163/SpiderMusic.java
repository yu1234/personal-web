package com.yu.crawlers.music163;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.Seimi;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.yu.crawlers.implement.SpiderInfoCallback;
import com.yu.crawlers.implement.SearchType;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuliu on 2017/11/27 0027.
 */
@Crawler(name = "SpiderMusic")
public class SpiderMusic extends BaseSeimiCrawler {

    private static SpiderInfoCallback spiderInfoCallback;
    private static SearchType searchType;
    private static Map<String, String> urlMap;
    private static Map<String, String> params;

    public String[] startUrls() {
        if (ObjectUtils.allNotNull(urlMap, searchType)) {
            if (ObjectUtils.allNotNull(urlMap.get(searchType.getType()))) {
                String paramStr = this.spliceParams(params);
                return new String[]{urlMap.get(searchType.getType()) + "?" + paramStr};
            } else {
                return new String[0];
            }
        } else {
            return new String[0];
        }

    }

    public void start(Response response) {
        if ("playList".equals(searchType.getType())) {
            this.getPlayList(response);
        }

    }

    /**
     * 获取歌单列表
     */
    private void getPlayList(Response response) {
        List r = new ArrayList();
        JXDocument doc = response.document();
        try {
            List<Object> playListIds = doc.sel("//ul[@class='f-hide']/li/a/@href[re:match('/song?id=\\s*(\\d*)')]");

            if (playListIds != null) {
                for (Object obj : playListIds) {
                    String url = obj.toString();
                    String id = StrUtils.parseUrlParam(url, "id");
                    r.add(id);
                }
            }

        } catch (XpathSyntaxErrorException e) {
            e.printStackTrace();
        }
        if (ObjectUtils.allNotNull(SpiderMusic.spiderInfoCallback)) {
            SpiderMusic.spiderInfoCallback.successCallback(r);
        }
    }

    /**
     * 开始爬虫
     *
     * @param
     */
    public void run(SearchType _searchType, Map<String, String> _params) {
        searchType = _searchType;
        params = _params;
        Seimi s = new Seimi();
        s.goRun("SpiderMusic");
    }

    public <T> void run(SearchType _searchType, Map<String, String> _params, SpiderInfoCallback<T> _spiderInfoCallback) {
        searchType = _searchType;
        params = _params;
        spiderInfoCallback = _spiderInfoCallback;
        Seimi s = new Seimi();
        s.goRun("SpiderMusic");
    }

    /**
     * 参数拼接
     *
     * @return
     */
    private String spliceParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer("");
        if (ObjectUtils.allNotNull(params)) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (ObjectUtils.allNotNull(key, value)) {
                    sb.append(key + "=" + value + "&");
                }
            }
        }
        return sb.toString();
    }

    public Map<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map<String, String> _urlMap) {
        urlMap = _urlMap;
    }
}
