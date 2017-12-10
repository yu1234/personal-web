package com.yu.music.player.controller;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import com.yu.music.player.Service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@RestController
public class MusicController {
    @Autowired
    private IMusicService musicService;
    @GetMapping("/")
    public Mono<Song> welcome() {
      return   musicService.getSongById("33233635");
    }

    /**
     * 获取歌单信息
     * @param id
     * @return
     */
    @GetMapping("/api/playlist/{id}")
    public Mono<String> getPlayList(@PathVariable("id") String id) {
        return  musicService.getSongSheet(id);
    }
}
