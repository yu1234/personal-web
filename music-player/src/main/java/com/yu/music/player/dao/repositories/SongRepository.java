package com.yu.music.player.dao.repositories;

import com.yu.crawlers.bean.Song;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongRepository extends ReactiveMongoRepository<Song,String> {
}
