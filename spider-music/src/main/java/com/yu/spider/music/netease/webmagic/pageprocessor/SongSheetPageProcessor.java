package com.yu.spider.music.netease.webmagic.pageprocessor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.bean.SongSheet;
import com.yu.spider.music.dao.repositories.SongSheetRepository;
import com.yu.spider.music.implement.BasePageProcessor;
import com.yu.spider.music.implement.SongSheetType;
import com.yu.spider.music.netease.webmagic.PageProcessorMain;
import com.yu.spider.music.utils.SpringUtil;
import com.yu.utils.JSONUtil;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;
import java.util.function.Consumer;

public class SongSheetPageProcessor implements BasePageProcessor, Pipeline {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000).setCharset("utf-8");
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
    //Pipeline key
    private final static String PIPELINE_KEY_SONGS = "songs";

    private SongSheet songSheet;

    private Consumer<SongSheet> callback;

    private SongSheetRepository songSheetRepository;

    private Request[] requests;

    private String url = "http://music.163.com/api/v3/playlist/detail";

    public SongSheetPageProcessor(SongSheetType type, String... ids) {
        this.setParams(type, ids);
        init();
    }

    public SongSheetPageProcessor(String... ids) {
        this.setParams(SongSheetType.PLAY_LIST, ids);
        init();
    }

    public SongSheetPageProcessor(Request... requests) {
        this.requests = requests;
        init();
    }

    private void init() {
        this.songSheetRepository = SpringUtil.getBean(SongSheetRepository.class);

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
     * 设置参数
     *
     * @param ids
     */
    public void setParams(SongSheetType type, String... ids) {
        if (ObjectUtils.allNotNull(ids) && ids.length > 0) {
            this.requests = new Request[ids.length];
            for (int i = 0, len = ids.length; i < len; i++) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", ids[i]);
                params.put("n", "1000");
                Request request = new Request(url);
                request.setMethod(HttpConstant.Method.POST);
                request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
                Map<String, Object> extras = new HashMap<>();
                extras.put("type", type.getValue());
                request.setExtras(extras);
                this.requests[i] = request;
            }
        }
    }

    /**
     * 设置通道
     *
     * @return
     */
    @Override
    public Pipeline[] getPipelines() {
        return new Pipeline[]{this};
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
            String content = json.toString();
            if (!StringUtils.isAllBlank(content)) {

                JSONUtil jsonUtil = JSONUtil.parseObject(content);
                String[] keys = new String[]{
                        KEY_ID, KEY_NAME, KEY_COVER, KEY_CREATOR_NAME, KEY_CREATOR_AVATAR, KEY_CREATOR_ID, KEY_DESCRIPTION, KEY_SONGS
                };
                Map<String, Object> valueMap = jsonUtil.getBatchValue(keys);

                //获取id
               String id= StrUtils.objectToString(valueMap.get(KEY_ID));
               if(ObjectUtils.allNotNull(id)){

                   this.songSheet = new SongSheet();
                   this.songSheet.setSource("netease");
                   this.songSheet.setDate(new Date());
                   this.songSheet.setId(id);
                   //获取歌单类型
                   int type = SongSheetType.PLAY_LIST.getValue();
                   if (ObjectUtils.allNotNull(page.getRequest().getExtra("type"))) {
                       type = (int) page.getRequest().getExtra("type");

                   }
                   this.songSheet.setType(type);
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
                       List<String> songList = new ArrayList<String>();
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
                           songList.add(song.getId());
                       }
                       this.songSheet.setSongs(songList);
                       if (songList.isEmpty()) {
                           //设置skip之后，这个页面的结果不会被Pipeline处理
                           page.setSkip(true);
                       }
                       page.putField(PIPELINE_KEY_SONGS, songList);
                   }

                   //查询是否存在
                   this.songSheetRepository.save(this.songSheet);
                   if (ObjectUtils.allNotNull(this.callback)) {
                       this.callback.accept(this.songSheet);
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

    /**
     * 回调
     *
     * @param callback
     */
    public void setCallback(Consumer<SongSheet> callback) {
        if (ObjectUtils.allNotNull(callback)) {
            this.callback = callback;
        }
    }

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> songs = resultItems.get(PIPELINE_KEY_SONGS);
        if (ObjectUtils.allNotNull(songs) && !songs.isEmpty()) {
            SongPageProcessor songPageProcessor = new SongPageProcessor(songs.toArray(new String[songs.size()]));
            PageProcessorMain.run(songPageProcessor);
        }
    }
}
