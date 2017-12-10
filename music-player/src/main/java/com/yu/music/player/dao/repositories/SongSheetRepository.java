package com.yu.music.player.dao.repositories;

import com.yu.crawlers.bean.SongSheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongSheetRepository extends ReactiveMongoRepository<SongSheet,String> {


}
