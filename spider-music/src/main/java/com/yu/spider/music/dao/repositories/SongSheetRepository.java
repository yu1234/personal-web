package com.yu.spider.music.dao.repositories;

import com.yu.spider.music.bean.SongSheet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongSheetRepository extends MongoRepository<SongSheet,String> {
}
