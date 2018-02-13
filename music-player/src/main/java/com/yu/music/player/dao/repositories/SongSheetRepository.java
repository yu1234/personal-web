package com.yu.music.player.dao.repositories;

import com.yu.spider.music.bean.SongSheet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongSheetRepository extends ReactiveMongoRepository<SongSheet, String> {
    Flux<SongSheet> findByType(int type);
}
