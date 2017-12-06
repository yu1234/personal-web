package com.yu.crawlers.bean;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
public class SongSheet {
    private String source;// 歌单来源
    private String id;// 歌单 id
    private String name;//歌单名称
    private String cover;//封面
    private String creatorName;// 列表创建者名字
    private String creatorAvatar;// 列表创建者头像
    private String creatorId;
    @DBRef
    private List<Song> songs;
    private String description;//描述

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
