package com.yu.crawlers.dao;

import com.yu.crawlers.bean.SongSheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongSheetRepository extends MongoRepository<SongSheet,String> {
}
