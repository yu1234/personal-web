package com.yu.crawlers.netease.parse;

import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.crawlers.bean.Album;
import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.dao.repositories.SongRepository;
import com.yu.crawlers.dao.repositories.SongSheetRepository;
import com.yu.crawlers.implement.ISpiderOperator;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.utils.JSONUtil;
import com.yu.crawlers.utils.SpringUtil;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.*;

/**
 * Created by yuliu on 2017/12/5 0005.
 * 解析歌单策略类
 */
public class SongSheetParse implements ISpiderOperator {

    //JsonObject获取key
    private final static String KEY_ID = "playlist.id";
    private final static String KEY_NAME = "playlist.name";
    private final static String KEY_COVER = "playlist.coverImgUrl";
    private final static String KEY_CREATOR_NAME = "playlist.creator.nickname";
    private final static String KEY_CREATOR_AVATAR = "playlist.creator.avatarUrl";
    private final static String KEY_CREATOR_ID = "playlist.creator.userId";
    private final static String KEY_DESCRIPTION = "playlist.description";
    private final static String KEY_SONGS = "playlist.tracks";
    private final static String KEY_SONG_ID = "id";
    private final static String KEY_SONG_NAME = "name";

    private SongSheet songSheet;

    private List<IParseCallback<SongSheet>> callbacks;

    private SongSheetRepository songSheetRepository;

    private SongRepository songRepository;

    private Request request;
    private String url = "http://music.163.com/api/v3/playlist/detail";
    private Map<String, String> params;

    public SongSheetParse() {
        init();
    }

    public SongSheetParse(String id) {
        this.setParams(id);
        init();
    }

    public SongSheetParse(Request request) {
        this.request = request;
        init();
    }

    private void init() {
        this.songSheetRepository = SpringUtil.getBean(SongSheetRepository.class);
        this.songRepository = SpringUtil.getBean(SongRepository.class);
        this.callbacks = new ArrayList<IParseCallback<SongSheet>>();
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
            this.request.setHttpMethod(HttpMethod.GET);
        }
    }

    /**
     * 解析返回json对象
     *
     * @param response
     */
    public void parse(Response response) {
        String content = response.getContent();
        if (!StringUtils.isAllBlank(content)) {
            this.songSheet = new SongSheet();
            this.songSheet.setSource("netease");
            this.songSheet.setDate(new Date());
            JSONUtil jsonUtil = JSONUtil.parseObject(content);
            String[] keys = new String[]{
                    KEY_ID, KEY_NAME, KEY_COVER, KEY_CREATOR_NAME, KEY_CREATOR_AVATAR, KEY_CREATOR_ID, KEY_DESCRIPTION, KEY_SONGS
            };
            Map<String, Object> valueMap = jsonUtil.getBatchValue(keys);
            //获取id
            this.songSheet.setId(StrUtils.objectToString(valueMap.get(KEY_ID)));
            //获取名称
            this.songSheet.setName(StrUtils.objectToString(valueMap.get(KEY_NAME)));
            //获取封面路径
            this.songSheet.setCover(StrUtils.objectToString(valueMap.get(KEY_COVER)));
            //获取创建者名字
            this.songSheet.setCreatorName(StrUtils.objectToString(valueMap.get(KEY_CREATOR_NAME)));
            //获取创建者头像
            this.songSheet.setCreatorAvatar(StrUtils.objectToString(valueMap.get(KEY_CREATOR_AVATAR)));
            //获取创建者id
            this.songSheet.setCreatorId(StrUtils.objectToString(valueMap.get(KEY_CREATOR_ID)));
            //获取歌单描述
            this.songSheet.setDescription(StrUtils.objectToString(valueMap.get(KEY_DESCRIPTION)));
            //获取歌单
            JSONArray songs = (JSONArray) valueMap.get(KEY_SONGS);
            if (ObjectUtils.allNotNull(songs)) {
                List<Song> songList = new ArrayList<Song>();
                for (int i = 0, len = songs.size(); i < len; i++) {
                    Song song = new Song();
                    song.setSource("netease");
                    song.setDate(new Date());
                    JSONObject jsonObject = songs.getJSONObject(i);
                    JSONUtil songJsonUtil = JSONUtil.parseObject(jsonObject);
                    String[] songKeys = new String[]{
                            KEY_SONG_ID, KEY_SONG_NAME
                    };
                    Map<String, Object> songValueMap = songJsonUtil.getBatchValue(songKeys);
                    song.setId(StrUtils.objectToString(songValueMap.get(KEY_SONG_ID)));
                    song.setName(StrUtils.objectToString(songValueMap.get(KEY_SONG_NAME)));
                    songList.add(song);
                }
                this.songSheet.setSongs(songList);
            }
            //数据保存
            this.songSheetRepository.save(this.songSheet);
            this.songRepository.saveAll(this.songSheet.getSongs());
            if (ObjectUtils.allNotNull(this.callbacks) && this.callbacks.size() > 0) {
                for (IParseCallback<SongSheet> callback : this.callbacks) {
                    callback.successCallback(this.songSheet);
                }
            }
        }
    }

    /**
     * 设置请求对象
     *
     * @return
     */
    public Request getRequest() {
        return  this.request;
    }

    /**
     * 获取请求对象
     *
     * @param request
     * @return
     */
    public void setRequest(Request request) {
        this.request = request;
    }
    /**
     * 回调
     *
     * @param callback
     */
    public void addCallback(IParseCallback<SongSheet> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callbacks.add(callback);
        }
    }

    /**
     * 设置参数
     *
     * @param id
     */
    public void setParams(String id) {
        if (!ObjectUtils.allNotNull(this.params)) {
            this.params = new HashMap<String, String>();
        }
        if (ObjectUtils.allNotNull(id)) {
            this.params.put("id",id);
            this.params.put("n","1000");
        }
    }
}
