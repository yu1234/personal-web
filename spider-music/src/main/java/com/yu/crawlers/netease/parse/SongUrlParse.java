package com.yu.crawlers.netease.parse;

import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yu.crawlers.bean.Song;
import com.yu.crawlers.dao.repositories.SongRepository;
import com.yu.crawlers.implement.ISpiderOperator;
import com.yu.crawlers.utils.SpringUtil;
import com.yu.utils.JSONUtil;
import com.yu.utils.StrUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Consumer;
/**
 * 歌曲路径解析类
 */
public class SongUrlParse implements ISpiderOperator {
    private final static String KEY_ROOT = "data";
    private final static String KEY_ID = "id";
    private final static String KEY_URL = "url";
    private Request[] requests;
    private String url = "http://music.163.com/api/song/enhance/player/url";
    private Consumer<List<Song>> callback;

    private SongRepository songRepository;

    public SongUrlParse(Request[] requests) {
        this.requests = requests;
        init();
    }

    public SongUrlParse(String... ids) {
        this.setParams(ids);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.songRepository = SpringUtil.getBean(SongRepository.class);
        //初始化请求对象
        if (ObjectUtils.allNotNull(this.requests) && this.requests.length > 0) {
            for (Request request : this.requests) {
                request.setCallBack("start");
            }
        }
    }
    /**
     * 解析回调
     *
     * @param response
     */
    @Override
    public void parse(Response response) {
        String content = response.getContent();
        List<Song> list=new ArrayList<>();
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
                    Optional<Song> optionalSong = songRepository.findById(songId);
                    if (optionalSong.isPresent()) {
                        String songUrl = StrUtils.objectToString(valueMap.get(KEY_URL));
                        Song song = optionalSong.get();
                        song.setUrl(songUrl);
                        songRepository.save(song);
                        list.add(song);
                    }
                }
            }

        }
        if (ObjectUtils.allNotNull(callback)) {
            callback.accept(list);
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
     * 获取请求对象
     *
     * @param request
     * @return
     */
    @Override
    public void setRequests(Request... request) {
        this.requests = request;
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
     * 设置参数
     *
     * @param ids
     */
    public void setParams(String... ids) {
        if (ObjectUtils.allNotNull(ids)) {
            Map params = new HashMap<String, String>();
            params.put("br", 320000 + "");
            params.put("ids", JSON.toJSONString(ids));
            Request request = Request.build(url, "start");
            request.setParams(params);
            request.setHttpMethod(HttpMethod.POST);
            this.requests = new Request[]{request};
        }
    }

    /**
     * 获取请求错误回调
     *
     * @return
     */
    @Override
    public Consumer<Request> getRequestErrorCallback() {
        return null;
    }

    /**
     * 设置请求错误回调
     *
     * @param requestErrorCallback
     * @return
     */
    @Override
    public void setRequestErrorCallback(Consumer<Request> requestErrorCallback) {

    }
}
