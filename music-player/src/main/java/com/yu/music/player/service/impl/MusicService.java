package com.yu.music.player.service.impl;

import com.alibaba.fastjson.JSON;
import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.crawlers.implement.SongSheetType;
import com.yu.crawlers.netease.SpiderMusic;
import com.yu.crawlers.netease.parse.SongParse;
import com.yu.crawlers.netease.parse.SongSheetParse;
import com.yu.crawlers.servic.ITimerService;
import com.yu.crawlers.servic.impl.TimerService;
import com.yu.crawlers.utils.SpringUtil;
import com.yu.music.player.dao.repositories.SongRepository;
import com.yu.music.player.service.IMusicService;
import com.yu.music.player.dao.ISongSheetDao;
import com.yu.music.player.dao.repositories.SongSheetRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Service
public class MusicService implements IMusicService {
    @Autowired
    private SongSheetRepository songSheetRepository;
    @Autowired
    private SongRepository songRepository;

    private ITimerService timerService=SpringUtil.getBean(TimerService.class);


    @Override
    public Mono<Song> getSongById(String id) {
        Mono<Song> mono = Mono.create(s -> {
            SongParse playSheetParse = new SongParse(id);
            playSheetParse.setCallback(songs -> {
                if (ObjectUtils.allNotNull(songs) && songs.size() > 0) {
                    s.success(songs.get(0));
                }
            });
            SpiderMusic.run(playSheetParse);
        });
        return this.songRepository.findById(id).switchIfEmpty(mono);


    }


    /**
     * 根据type 获取歌单
     *
     * @param type
     * @return
     */
    @Override
    public Flux<SongSheet> getSongSheetByType(int type) {
        return this.songSheetRepository.findByType(type);
    }

    /**
     * 根据歌单id 获取歌单歌曲
     *
     * @param id
     * @return
     */
    @Override
    public Flux<Song> getSongsBySongSheetId(String id) {
        SongSheet songSheet = this.getSongSheetById(id).block();
        if (ObjectUtils.allNotNull(songSheet) && ObjectUtils.allNotNull(songSheet.getSongs()) && !songSheet.getSongs().isEmpty()) {
            List<Mono<Song>> monos = songSheet.getSongs().stream().map(songId -> this.getSongById(songId)).collect(Collectors.toList());
            return Flux.concat(monos);
        }
        return Flux.empty();
    }

    /**
     * 根据歌单id 获取歌单
     *
     * @param id
     * @return
     */
    public Mono<SongSheet> getSongSheetById(String id) {
        Mono<SongSheet> mono = Mono.create(s -> {
            SongSheetParse songSheetParse = new SongSheetParse(id);
            songSheetParse.setCallback(songSheet -> s.success(songSheet));
            SpiderMusic.run(songSheetParse);
        });
        return this.songSheetRepository.findById(id).switchIfEmpty(mono);
    }

    /**
     *
     */

}
