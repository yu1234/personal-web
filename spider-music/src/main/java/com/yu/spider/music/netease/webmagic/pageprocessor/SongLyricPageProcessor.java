package com.yu.spider.music.netease.webmagic.pageprocessor;

import com.yu.spider.music.bean.Lyric;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.dao.repositories.SongRepository;
import com.yu.spider.music.implement.BasePageProcessor;
import com.yu.spider.music.utils.SpringUtil;
import com.yu.utils.JSONUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class SongLyricPageProcessor implements BasePageProcessor {
    private Site site = Site.me().setRetryTimes(3).setCharset("utf-8").setSleepTime(5000);
    private final static String KEY_ID = "id";
    private final static String KEY_SOURCE_LYRIC = "lrc.lyric";
    private final static String KEY_TRANSLATION_LYRIC = "tlyric.lyric";

    private Request[] requests;
    private String url = "http://music.163.com/api/song/lyric";
    private Consumer<Map<Object, Lyric>> callback;
    private Map<Object, Lyric> lyricMap = new HashMap<>();
    private SongRepository songRepository;

    public SongLyricPageProcessor(Request[] requests) {
        this.requests = requests;
        init();
    }

    public SongLyricPageProcessor(String... ids) {
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
     * 设置参数
     *
     * @param ids
     */
    public void setParams(String... ids) {
        if (ObjectUtils.allNotNull(ids)) {
            this.requests = new Request[ids.length];
            int i = 0;
            for (String id : ids) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", id);
                params.put("os", "linux");
                params.put("lv", -1);
                params.put("kv", -1);
                params.put("tv", -1);
                Request request = new Request(url);
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
                Map<String, Object> extras = new HashMap<>();
                extras.put(KEY_ID, id);
                request.setExtras(extras);
                this.requests[i] = request;
                i++;
            }


        }
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
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Json json = page.getJson();
        if (json != null) {
            this.parse(json.toString(), page.getRequest().getExtras().get(KEY_ID));
        }
        if (ObjectUtils.allNotNull(this.callback)) {
            this.callback.accept(this.lyricMap);
        }
    }

    /**
     * 解析回调
     *
     * @param content
     */
    private void parse(String content, Object id) {
        if (!StringUtils.isAllBlank(content)) {
            JSONUtil jsonUtil = JSONUtil.parseObject(content);
            Lyric lyric = new Lyric();
            lyric.setSourceLyric(jsonUtil.get(KEY_SOURCE_LYRIC, String.class));
            lyric.setTranslationLyric(jsonUtil.get(KEY_TRANSLATION_LYRIC, String.class));
            lyric.setId((String) id);
            this.lyricMap.put(id, lyric);
            //保存数据库
            Optional<Song> optionalSong = this.songRepository.findById((String) id);
            if (optionalSong.isPresent()) {
                Song song = optionalSong.get();
                song.setLyric(lyric);
                this.songRepository.save(song);
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

    /**
     * 回调
     *
     * @param callback
     */
    public void setCallback(Consumer<Map<Object, Lyric>> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callback = callback;
        }

    }
}
