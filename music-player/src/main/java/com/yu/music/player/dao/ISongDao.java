package com.yu.music.player.dao;

import com.yu.spider.music.bean.Song;
import reactor.core.publisher.Flux;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface ISongDao extends IBaseDao<Song,String> {
    /**
     * 批量获取歌曲
     * @param ids 歌曲ids
     * @return
     */
    Flux<Song> findSongsBySongSheetId(String... ids);
}
