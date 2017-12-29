package com.yu.music.player.controller;

import com.yu.spider.music.bean.Lyric;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.bean.SongSheet;
import com.yu.music.player.service.IMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/12/7 0007.
 */

@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    private IMusicService musicService;

    @GetMapping("/api/song/{id}")
    public Mono<Song> getSongById(@PathVariable("id") String id) {
        return musicService.getSongById(id);
    }

    /**
     *  根据歌曲id 获取最新url
     * @param id
     * @return
     */
    @GetMapping("/api/song/url/{id}")
    public Mono<String> getUrlBySongId(@PathVariable("id") String id) {
        return musicService.getUrlBySongId(id);
    }

    /**
     * 获取歌单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/api/songSheet/{id}")
    public Mono<SongSheet> getPlayList(@PathVariable("id") String id) {
        return musicService.getSongSheetById(id);
    }

    /**
     * 根据type 获取歌单
     *
     * @param type
     * @return
     */
    @GetMapping("/api/songSheet/type/{type}")
    public Flux<SongSheet> getSongSheetByType(@PathVariable("type") int type) {
        return musicService.getSongSheetByType(type);
    }

    /**
     * 根据歌单id 获取歌单歌曲
     *
     * @param id
     * @return
     */
    @GetMapping("/api/songSheet/songs/{id}")
    public Flux<Song> getSongsBySongSheetId(@PathVariable("id") String id) {
        return musicService.getSongsBySongSheetId(id);
    }
    /**
     * 根据歌词id 获取歌词
     *
     * @param id
     * @return
     */
    @GetMapping("/api/song/lyric/{id}")
    public Mono<Lyric> getLyricId(@PathVariable("id") String id) {
        return musicService.getLyricByLyricId(id);
    }
}
