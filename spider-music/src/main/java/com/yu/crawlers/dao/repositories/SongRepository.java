package com.yu.crawlers.dao.repositories;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongRepository extends MongoRepository<Song,String> {
}
