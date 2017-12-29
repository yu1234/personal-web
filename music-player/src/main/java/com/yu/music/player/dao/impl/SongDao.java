package com.yu.music.player.dao.impl;


import com.yu.music.player.dao.ISongDao;
import com.yu.spider.music.bean.Song;
import com.yu.spider.music.netease.webmagic.PageProcessorMain;
import com.yu.spider.music.netease.webmagic.pageprocessor.SongPageProcessor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.StreamUtils;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
@Repository
public class SongDao implements ISongDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 批量获取歌曲
     *
     * @param ids 歌曲ids
     * @return
     */
    @Override
    public Flux<Song> findSongsBySongSheetId(String... ids) {
        Assert.notNull(ids, "ids不能为空！");
        return Flux.from(r -> {
            Query query = new Query();
            query.addCriteria(Criteria.where(Song.ATTR_ID).in(Streamable.of(ids).stream().collect(StreamUtils.toUnmodifiableList())));
            List<Song> songs = mongoTemplate.find(query, Song.class);
            if (ObjectUtils.allNotNull(songs) && songs.size() >= ids.length) {
                songs.forEach(song -> r.onNext(song));
                r.onComplete();
            } else {
                Map<String, Song> songMap = songs.stream().collect(Collectors.toMap(Song::getId, song -> song));
                List<String> notExists = new ArrayList<>();
                Streamable.of(ids).stream().collect(Collectors.toList()).forEach(songId -> {
                    if (ObjectUtils.allNotNull(songMap.get(songId))) {
                        r.onNext(songMap.get(songId));
                    } else {
                        notExists.add(songId);
                    }
                });
                if (!notExists.isEmpty()) {
                    SongPageProcessor songPageProcessor = new SongPageProcessor(notExists.toArray(new String[notExists.size()]));
                    songPageProcessor.setCallback(webSongs -> {
                        webSongs.forEach(webSong -> r.onNext(webSong));
                        r.onComplete();
                    });
                    PageProcessorMain.run(songPageProcessor);
                } else {
                    r.onComplete();
                }
            }
        });

    }
}
