package com.yu.spider.music.netease.webmagic.pageprocessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.spider.music.bean.Album;
import com.yu.spider.music.bean.Artist;
import com.yu.spider.music.bean.Lyric;
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

public class SongPageProcessor implements BasePageProcessor {
    private final static String KEY_ROOT = "songs";
    private final static String KEY_ID = "id";
    private final static String KEY_NAME = "name";
    private final static String KEY_ARTIST = "ar";
    private final static String KEY_ALBUM = "al";
    private Request[] requests;
    private String url = "http://music.163.com/api/v3/song/detail";
    private List<Song> songs;
    private Site site = Site.me().setRetryTimes(3).setCharset("utf-8");
    private SongRepository songRepository;
    private Consumer<List<Song>> callback;


    public SongPageProcessor(String... ids) {
        this.setParams(ids);
        init();
    }

    public SongPageProcessor(Request... requests) {
        this.requests = requests;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.songRepository = SpringUtil.getBean(SongRepository.class);
    }

    @Override
    public void process(Page page) {
        Json json = page.getJson();
        if (json != null) {
            this.parse(json.toString());
        }
        if (ObjectUtils.allNotNull(this.callback)) {
            this.callback.accept(this.songs);
        }
    }

    /**
     * 结果解析
     *
     * @param content
     */
    public void parse(String content) {
        if (!StringUtils.isAllBlank(content)) {
            this.songs = new ArrayList<Song>();
            JSONUtil jsonUtil = JSONUtil.parseObject(content);
            JSONArray jsonArray = jsonUtil.get(KEY_ROOT, JSONArray.class);
            if (ObjectUtils.allNotNull(jsonArray)) {
                for (int i = 0, len = jsonArray.size(); i < len; i++) {
                    jsonUtil.setJsonObject(jsonArray.getJSONObject(i));
                    String[] keys = new String[]{
                            KEY_ARTIST, KEY_ALBUM, KEY_ID, KEY_ARTIST, KEY_NAME
                    };
                    Map<String, Object> valueMap = jsonUtil.getBatchValue(keys);
                    Song song = new Song();
                    song.setSource("netease");
                    song.setDate(new Date());
                    song.setId(StrUtils.objectToString(valueMap.get(KEY_ID)));
                    song.setName(StrUtils.objectToString(valueMap.get(KEY_NAME)));
                    //设置专辑
                    if (ObjectUtils.allNotNull(valueMap.get(KEY_ALBUM))) {
                        JSONObject jsonObject = (JSONObject) valueMap.get(KEY_ALBUM);
                        if (ObjectUtils.allNotNull(jsonObject)) {
                            Album album = jsonObject.toJavaObject(Album.class);
                            song.setAlbum(album);
                        }

                    }
                    //设置歌手
                    if (ObjectUtils.allNotNull(valueMap.get(KEY_ARTIST))) {
                        JSONArray array = (JSONArray) valueMap.get(KEY_ARTIST);
                        if (ObjectUtils.allNotNull(array)) {
                            List<Artist> artists = array.toJavaList(Artist.class);
                            song.setArtists(artists);
                        }
                    }
                    //设置歌词id
                    if (ObjectUtils.allNotNull(song.getId())) {
                        Lyric lyric = new Lyric();
                        lyric.setId(song.getId());
                        song.setLyric(lyric);
                    }
                    this.songs.add(song);
                    Optional<Song> optionalSong = this.songRepository.findById(song.getId());
                    if (!optionalSong.isPresent()) {
                        this.songRepository.save(song);
                    }
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 设置参数
     *
     * @param ids
     */
    public void setParams(String... ids) {
        if (ObjectUtils.allNotNull(ids)) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (String id : ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                list.add(map);
            }
            Map params = new HashMap<String, String>();
            params.put("c", JSON.toJSONString(list));
            Request request = new Request(url);
            request.setMethod(HttpConstant.Method.POST);
            request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
            this.requests = new Request[]{request};
        }
    }

    /**
     * 回调
     *
     * @param callback
     */
    public void setCallback(Consumer<List<Song>> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callback = callback;
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
}
