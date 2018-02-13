package com.yu.music.player.dao.repositories;

import com.yu.spider.music.bean.Song;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongRepository extends ReactiveMongoRepository<Song, String> {


}
