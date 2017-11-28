package com.yu.crawlers.music163;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.core.Seimi;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.implement.SpiderInfoCallback;
import com.yu.crawlers.implement.SearchType;
import com.yu.utils.IoUtils;
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
        } else if ("playListInfo".equals(searchType.getType())) {
            this.getPlayListInfo(response);
        }

    }

    /**
     * 获取歌单列表
     */
    private void getPlayList(Response response) {
        if (ObjectUtils.allNotNull(response)) {
            List r = new ArrayList();
            JXDocument doc = response.document();
            try {
                List<Object> playListIds = doc.sel("//ul[@class='f-hide']/li/a/@href]");

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
    }

    /**
     * 获取歌单信息
     */
    private void getPlayListInfo(Response response) {
        if (ObjectUtils.allNotNull(response)) {
            SongSheet songSheet = new SongSheet();
            //  IoUtils.WriteStringToFile("F:/spiderFile/response.html",response.getContent());
            JXDocument doc = response.document();
            try {
                //获取歌单信息
                List<JXNode> infoA = doc.selN("//div[@id='content-operation']/a[@data-res-action='share']");
                if (infoA != null && !infoA.isEmpty()) {
                    JXNode a = infoA.get(0);
                    List<JXNode> id = a.sel("@data-res-id");
                    if (ObjectUtils.allNotNull(id) && !id.isEmpty()) {
                        songSheet.setId(id.get(0).toString());
                    }
                    List<JXNode> name = a.sel("@data-res-name");
                    if (ObjectUtils.allNotNull(name) && !name.isEmpty()) {
                        songSheet.setName(name.get(0).toString());
                    }
                    List<JXNode> author = a.sel("@data-res-author");
                    if (ObjectUtils.allNotNull(author) && !author.isEmpty()) {
                        songSheet.setCreatorName(author.get(0).toString());
                    }
                    List<JXNode> cover = a.sel("@data-res-pic");
                    if (ObjectUtils.allNotNull(cover) && !cover.isEmpty()) {
                        songSheet.setCover(cover.get(0).toString());
                    }
                }
                //获取创建者信息
                List<JXNode> creatorA = doc.selN("//a[@class='s-fc7']");
                if (creatorA != null && !creatorA.isEmpty()) {
                    JXNode a = creatorA.get(0);
                    List<JXNode> href = a.sel("@href");
                    if (ObjectUtils.allNotNull(href) && !href.isEmpty()) {
                        songSheet.setCreatorId(StrUtils.parseUrlParam(href.get(0).toString(), "id"));
                    }
                }
                //获取创建者头像
                List<JXNode> creatorAvatarImg = doc.selN("//a[@class='face']/img");
                if (creatorAvatarImg != null && !creatorAvatarImg.isEmpty()) {
                    JXNode img = creatorAvatarImg.get(0);
                    List<JXNode> src = img.sel("@src");
                    if (ObjectUtils.allNotNull(src) && !src.isEmpty()) {
                        String url = src.get(0).toString();
                        String[] group = url.split("\\?");
                        if (ObjectUtils.allNotNull(group) && group.length > 0) {
                            songSheet.setCreatorAvatar(group[0]);
                        }
                    }
                }
            } catch (XpathSyntaxErrorException e) {
                e.printStackTrace();
            }
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
