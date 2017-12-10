package com.yu.music.player.handler;

import com.yu.crawlers.bean.Song;
import com.yu.music.player.Service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Component
public class MusicHandler {
    @Autowired
    private IMusicService musicService;

    public Mono getSongById(ServerRequest request) {

        return ServerResponse
                .ok()
                .body(this.musicService.getSongById("33233635"), Song.class);
    }

}
