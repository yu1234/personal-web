package com.yu.music.player.service.impl;

import com.alibaba.fastjson.JSON;
import com.yu.music.player.dao.ISongDao;
import com.yu.music.player.dao.repositories.SongRepository;
import com.yu.music.player.dao.repositories.SongSheetRepository;
import com.yu.music.player.service.IMusicService;
import com.yu.spider.music.bean.Lyric;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.bean.SongSheet;
import com.yu.spider.music.netease.webmagic.PageProcessorMain;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongLyricPageProcessor;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongPageProcessor;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongSheetPageProcessor;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongUrlPageProcessor;
import com.yu.utils.IoUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Service
public class MusicService implements IMusicService {
    @Autowired
    private SongSheetRepository songSheetRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ISongDao songDao;


    @Override
    public Mono<Song> getSongById(String id) {
        Mono<Song> mono = Mono.create(s -> {
            SongPageProcessor songPageProcessor = new SongPageProcessor(id);
            songPageProcessor.setCallback(songs -> {
                if (ObjectUtils.allNotNull(songs) && songs.size() > 0) {
                    s.success(songs.get(0));
                }
            });
            PageProcessorMain.run(songPageProcessor);
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
        return this.getSongSheetById(id)
                .map(songSheet -> songSheet.getSongs())
                .flatMapMany(songIds -> this.songDao.findSongsBySongSheetId(songIds.toArray(new String[songIds.size()])));
    }

    /**
     * 根据歌单id 获取歌单
     *
     * @param id
     * @return
     */
    public Mono<SongSheet> getSongSheetById(String id) {
        Mono<SongSheet> mono = Mono.create(s -> {
            SongSheetPageProcessor songSheetPageProcessor = new SongSheetPageProcessor(id);
            songSheetPageProcessor.setCallback(songSheet -> s.success(songSheet));
            PageProcessorMain.run(songSheetPageProcessor);
        });
        return this.songSheetRepository.findById(id).switchIfEmpty(mono);
    }

    /**
     * 根据歌曲id 获取最新url
     *
     * @param id
     * @return
     */
    @Override
    public Mono<String> getUrlBySongId(String id) {
        Mono<String> mono = Mono.create(s -> {
            SongUrlPageProcessor songUrlPageProcessor = new SongUrlPageProcessor(id);
            songUrlPageProcessor.setCallback(urlMap -> {
                if (urlMap.get(id) != null) {
                    s.success(JSON.toJSONString(urlMap.get(id)));
                }

            });
            PageProcessorMain.run(songUrlPageProcessor);
        });
        return this.songRepository.findById(id).flatMap(song -> {
            if (IoUtils.existsUrl(song.getUrl())) {
                return Mono.create(s -> s.success(JSON.toJSONString(song.getUrl())));
            } else {
                return mono;
            }
        }).switchIfEmpty(mono);
    }

    /**
     * 根据歌词id 获取歌词
     * @param id
     * @return
     */
    @Override
    public Mono<Lyric> getLyricByLyricId(String id) {
        Mono<Lyric> mono = Mono.create(s -> {
            SongLyricPageProcessor songLyricPageProcessor = new SongLyricPageProcessor(id);
            songLyricPageProcessor.setCallback(map -> {
                if (map.get(id) != null) {
                    s.success(map.get(id));
                }
            });
            PageProcessorMain.run(songLyricPageProcessor);
        });
        return this.songRepository.findById(id).flatMap(song -> {
            if (ObjectUtils.allNotNull(song.getLyric())&&ObjectUtils.allNotNull(song.getLyric().getSourceLyric())) {
                return Mono.create(s -> s.success(song.getLyric()));
            } else {
                return mono;
            }
        }).switchIfEmpty(mono);
    }

}
