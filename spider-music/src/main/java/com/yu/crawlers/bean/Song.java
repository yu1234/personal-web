package com.yu.crawlers.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
public class Song extends BaseObject {
    private String name; // 音乐名字
    private List<Artist> artists;// 艺术家
    private Album album;// 专辑名字
    private String source;// 音乐来源
    private Lyric lyric;// 歌词
    private String url; // mp3链接
    private Date date;//日期


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Lyric getLyric() {
        return lyric;
    }

    public void setLyric(Lyric lyric) {
        this.lyric = lyric;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
