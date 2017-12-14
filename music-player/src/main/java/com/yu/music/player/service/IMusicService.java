package com.yu.music.player.service;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
public interface IMusicService {
    Mono getSongById(String id);




    /**
     * 根据type 获取歌单
     * @param type
     * @return
     */
    Flux<SongSheet> getSongSheetByType(int type);

    /**
     * 根据歌单id 获取歌单歌曲
     *
     * @param id
     * @return
     */
    Flux<Song> getSongsBySongSheetId(String id);
    /**
     * 根据歌单id 获取歌单
     * @param id
     * @return
     */
     Mono<SongSheet> getSongSheetById(String id);
}
