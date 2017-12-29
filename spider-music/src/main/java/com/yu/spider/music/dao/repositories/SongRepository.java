package com.yu.spider.music.dao.repositories;

import com.yu.spider.music.bean.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongRepository extends MongoRepository<Song,String> {
}
