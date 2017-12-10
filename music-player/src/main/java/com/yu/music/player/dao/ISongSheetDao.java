package com.yu.music.player.dao;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface ISongSheetDao {

    /**
     * 是否存在该条记录
     * @param id 主键
     * @return
     */
    boolean isExist(String id);
}
