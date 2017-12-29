package com.yu.spider.music.implement;

/**
 * Created by yuliu on 2017/11/27 0027.
 * 查询类型
 */
public enum SongSheetType {
    PLAY_LIST(1), TOP_LIST(2);
    private int value;

    SongSheetType(int i) {
        this.value=i;
    }

    public int getValue() {
        return value;
    }

}
