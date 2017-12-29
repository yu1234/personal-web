package com.yu.spider.music.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
@Data
public class Song extends BaseObject {
    public final static String ATTR_NAME="name";
    public final static String ATTR_ARTISTS="artists";
    public final static String ATTR_ALBUM="album";
    public final static String ATTR_SOURCE="source";
    public final static String ATTR_LYRIC="lyric";
    public final static String ATTR_URL="url";
    public final static String ATTR_DATE="date";

    private String name; // 音乐名字
    private List<Artist> artists;// 艺术家
    private Album album;// 专辑名字
    private String source;// 音乐来源
    private Lyric lyric;// 歌词
    private String url; // mp3链接
    private Date date;//日期



}
