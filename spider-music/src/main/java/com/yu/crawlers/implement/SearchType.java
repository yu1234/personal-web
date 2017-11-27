package com.yu.crawlers.implement;

/**
 * Created by yuliu on 2017/11/27 0027.
 * 查询类型
 */
public enum SearchType {
    PLAY_LIST("playList"), SONG("song"), PLAY_LIST_INFO("playListInfo");
    private String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
