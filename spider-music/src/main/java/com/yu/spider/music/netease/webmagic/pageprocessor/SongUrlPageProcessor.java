package com.yu.spider.music.netease.webmagic.pageprocessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.dao.repositories.SongRepository;
import com.yu.spider.music.implement.BasePageProcessor;
import com.yu.spider.music.utils.SpringUtil;
import com.yu.utils.JSONUtil;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;
import java.util.function.Consumer;

/**
 * 歌曲路径解析类
 */
public class SongUrlPageProcessor implements BasePageProcessor {
    private Site site = Site.me().setRetryTimes(3).setCharset("utf-8");
    private final static String KEY_ROOT = "data";
    private final static String KEY_ID = "id";
    private final static String KEY_URL = "url";
    private Request[] requests;
    private String url = "http://music.163.com/api/song/enhance/player/url";
    private Consumer<Map<Object, String>> callback;
    private Map<Object, String> urlMap = new HashMap<>();

    private SongRepository songRepository;

    public SongUrlPageProcessor(Request[] requests) {
        this.requests = requests;
        init();
    }

    public SongUrlPageProcessor(String... ids) {
        this.setParams(ids);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.songRepository = SpringUtil.getBean(SongRepository.class);
    }


    /**
     * 设置请求对象
     *
     * @return
     */
    @Override
    public Request[] getRequests() {
        return this.requests;
    }

    /**
     * 设置通道
     *
     * @return
     */
    @Override
    public Pipeline[] getPipelines() {
        return new Pipeline[0];
    }

    /**
     * 获取请求对象
     *
     * @param request
     * @return
     */
    public void setRequests(Request... request) {
        this.requests = request;
    }

    /**
     * 回调
     *
     * @param callback
     */
    public void setCallback(Consumer<Map<Object, String>> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callback = callback;
        }

    }

    /**
     * 设置参数
     *
     * @param ids
     */
    public void setParams(String... ids) {
        if (ObjectUtils.allNotNull(ids)) {
            Map params = new HashMap<String, String>();
            params.put("br", 320000 + "");
            params.put("ids", JSON.toJSONString(ids));
            Request request = new Request(url);
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            this.requests = new Request[]{request};
        }
    }


    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Json json = page.getJson();
        if (json != null) {
            this.parse(json.toString());
        }
        if (ObjectUtils.allNotNull(this.callback)) {
            this.callback.accept(this.urlMap);
        }
    }

    /**
     * 解析回调
     *
     * @param content
     */
    public void parse(String content) {
        if (!StringUtils.isAllBlank(content)) {
            JSONUtil jsonUtil = JSONUtil.parseObject(content);
            JSONArray jsonArray = jsonUtil.get(KEY_ROOT, JSONArray.class);
            if (ObjectUtils.allNotNull(jsonArray)) {
                for (int i = 0, len = jsonArray.size(); i < len; i++) {
                    jsonUtil.setJsonObject(jsonArray.getJSONObject(i));
                    String[] keys = new String[]{
                            KEY_ID, KEY_URL
                    };
                    Map<String, Object> valueMap = jsonUtil.getBatchValue(keys);
                    String songId = StrUtils.objectToString(valueMap.get(KEY_ID));
                    String songUrl = StrUtils.objectToString(valueMap.get(KEY_URL));
                    urlMap.put(songId, songUrl);
                    Optional<Song> optionalSong = this.songRepository.findById(songId);
                    if (optionalSong.isPresent()) {
                        Song song = optionalSong.get();
                        song.setUrl(songUrl);
                        this.songRepository.save(song);
                    }

                }
            }

        }
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return site;
    }
}
