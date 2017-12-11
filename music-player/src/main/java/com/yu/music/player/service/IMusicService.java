package com.yu.music.player.service;

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
