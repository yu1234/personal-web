package com.yu.music.player.Service;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
public interface IMusicService {
    Mono getSongById(String id);

    /**
     * 获取歌单列表
     * @param id
     * @return
     */
    Mono<String> getSongSheet(String id);
}
