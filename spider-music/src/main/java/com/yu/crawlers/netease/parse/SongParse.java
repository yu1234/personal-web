package com.yu.crawlers.netease.parse;

import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.crawlers.bean.*;
import com.yu.crawlers.dao.repositories.SongRepository;
import com.yu.crawlers.dao.repositories.SongSheetRepository;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.crawlers.implement.ISpiderOperator;
import com.yu.crawlers.utils.SpringUtil;
import com.yu.utils.JSONUtil;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
public class SongParse implements ISpiderOperator {
    private final static String KEY_ROOT = "songs";
    private final static String KEY_ID = "id";
    private final static String KEY_NAME = "name";
    private final static String KEY_ARTIST = "ar";
    private final static String KEY_ALBUM = "al";

    private SongRepository songRepository;
    private List<IParseCallback<List<Song>>> callbacks;

    private Request request;
    private String url = "http://music.163.com/api/v3/song/detail";
    private Map<String, String> params;
    private List<Song> songs;

    public SongParse() {
        init();
    }

    public SongParse(String... ids) {
        this.setParams(ids);
        init();
    }

    public SongParse(Request request) {
        this.request = request;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.songRepository = SpringUtil.getBean(SongRepository.class);
        this.callbacks = new ArrayList<IParseCallback<List<Song>>>();
        //初始化参数
        if (!ObjectUtils.allNotNull(this.params)) {
            this.params = new HashMap<String, String>();
        }
        //初始化请求对象
        if (ObjectUtils.allNotNull(this.request)) {
            this.request.setCallBack("start");
        } else {
            this.request = Request.build(url, "start");
            this.request.setParams(this.params);
            this.request.setHttpMethod(HttpMethod.POST);
        }
    }

    /**
     * 结果解析
     *
     * @param response
     */
    public void parse(Response response) {
        String content = response.getContent();
        if (!StringUtils.isAllBlank(content)) {
            this.songs = new ArrayList<Song>();
            JSONUtil jsonUtil = JSONUtil.parseObject(content);
            JSONArray jsonArray = jsonUtil.get(KEY_ROOT, JSONArray.class);
            if (ObjectUtils.allNotNull(jsonArray)) {
                for (int i = 0, len = jsonArray.size(); i < len; i++) {
                    jsonUtil.setJsonObject(jsonArray.getJSONObject(i));
                    String[] keys = new String[]{
                            KEY_ARTIST, KEY_ALBUM, KEY_ID, KEY_ARTIST
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
                }
            }
        }
        if (ObjectUtils.allNotNull(this.songs) && !this.songs.isEmpty()) {
            this.songRepository.saveAll(this.songs);
        }
        if (ObjectUtils.allNotNull(this.callbacks) && this.callbacks.size() > 0) {
            for (IParseCallback<List<Song>> callback : this.callbacks) {
                callback.successCallback(this.songs);
            }
        }
    }

    /**
     * 设置请求对象
     *
     * @return
     */
    public Request getRequest() {
        return this.request;
    }

    /**
     * 获取请求对象
     *
     * @return
     */
    public void setRequest(Request requests) {
        this.request = request;
    }


    /**
     * 设置参数
     *
     * @param ids
     */
    public void setParams(String... ids) {
        if (!ObjectUtils.allNotNull(this.params)) {
            this.params = new HashMap<String, String>();
        }
        if (ObjectUtils.allNotNull(ids)) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (String id : ids) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                list.add(map);
            }
            this.params.put("c", JSON.toJSONString(list));
        }
    }

    /**
     * 回调
     *
     * @param callback
     */
    public void addCallback(IParseCallback<List<Song>> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callbacks.add(callback);
        }
    }
}
