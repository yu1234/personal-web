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
import com.yu.crawlers.implement.IRequestErrorCallback;
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

    private IRequestErrorCallback requestErrorCallback;

    private Request[] requests;
    private String url = "http://music.163.com/api/v3/song/detail";
    private List<Song> songs;


    public SongParse(String... ids) {
        this.setParams(ids);
        init();
    }

    public SongParse(Request... requests) {
        this.requests = requests;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.songRepository = SpringUtil.getBean(SongRepository.class);
        this.callbacks = new ArrayList<IParseCallback<List<Song>>>();

        //初始化请求对象
        if (ObjectUtils.allNotNull(this.requests) && this.requests.length > 0) {
            for (Request request : this.requests) {
                request.setCallBack("start");
            }
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
    public Request[] getRequests() {
        return this.requests;
    }

    /**
     * 获取请求对象
     *
     * @return
     */
    public void setRequests(Request... requests) {
        this.requests = requests;
    }

    /**
     * 获取请求错误回调
     *
     * @return
     */
    public IRequestErrorCallback getRequestErrorCallback() {
        return this.requestErrorCallback;
    }

    /**
     * 设置请求错误回调
     *
     * @return
     */
    public void setRequestErrorCallback(IRequestErrorCallback requestErrorCallback) {
        this.requestErrorCallback=requestErrorCallback;
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
            Request request = Request.build(url, "start");
            request.setParams(params);
            request.setHttpMethod(HttpMethod.POST);
            this.requests = new Request[]{request};
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
