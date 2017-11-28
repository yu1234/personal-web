package com.yu.crawlers.bean;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
public class Song {
    private String id;// 音乐ID
    private String name; // 音乐名字
    private String artist;// 艺术家名字
    private String album;// 专辑名字
    private String source;// 音乐来源
    private String cover;// 专辑图片
    private String lyricId;// 歌词ID
    private String url; // mp3链接

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLyricId() {
        return lyricId;
    }

    public void setLyricId(String lyricId) {
        this.lyricId = lyricId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
