package com.yu.music.player.Service.impl;

import com.alibaba.fastjson.JSON;
import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.crawlers.netease.SpiderMusic;
import com.yu.crawlers.netease.parse.SongParse;
import com.yu.crawlers.netease.parse.SongSheetParse;
import com.yu.crawlers.utils.SpringUtil;
import com.yu.music.player.Service.IMusicService;
import com.yu.music.player.dao.ISongSheetDao;
import com.yu.music.player.dao.repositories.SongSheetRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Service
public class MusicService implements IMusicService {
    @Autowired
    private SongSheetRepository songSheetRepository;
    @Autowired
    private ISongSheetDao songSheetDao;

    @Override
    public Mono getSongById(String id) {

        Mono mono = Mono.create(s -> {

            SpiderMusic spiderMusic = SpringUtil.getBean(SpiderMusic.class);
            if (ObjectUtils.allNotNull(spiderMusic)) {
                SongParse playSheetParse = new SongParse(id);
                playSheetParse.addCallback(new IParseCallback<List<Song>>() {
                    @Override
                    public void successCallback(List<Song> songs) {
                        s.success(songs);
                    }
                });
                spiderMusic.run(playSheetParse);
            }
        });

        return mono;
    }

    /**
     * 获取歌单列表
     *
     * @param id
     * @return
     */
    @Override
    public Mono<String> getSongSheet(String id) {
        Mono<String> mono;
        mono = songSheetRepository.findById(id).map(v -> {
            return JSON.toJSONString(v);
        }).switchIfEmpty(Mono.create(s -> {
            SpiderMusic spiderMusic = SpringUtil.getBean(SpiderMusic.class);
            if (ObjectUtils.allNotNull(spiderMusic)) {
                SongSheetParse songSheetParse = new SongSheetParse(id);
                songSheetParse.addCallback(new IParseCallback<SongSheet>() {
                    @Override
                    public void successCallback(SongSheet songSheet) {
                        s.success(JSON.toJSONString(songSheet));
                    }
                });
                spiderMusic.run(songSheetParse);
            }
        }));
        return mono;
    }

}
