package com.yu.spider.music.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by yuliu on 2017/11/28 0028.
 */
@Data
public class SongSheet extends BaseObject {
    private String source;// 歌单来源
    private String name;//歌单名称
    private String cover;//封面
    private String creatorName;// 列表创建者名字
    private String creatorAvatar;// 列表创建者头像
    private String creatorId;
    private Date date;
    private String description;//描述
    private int type;//歌单类型
    private List<String> songs;


}
