package com.yu.crawlers.dao.impl;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.bean.SongSheet;
import com.yu.crawlers.dao.ISongDao;
import com.yu.crawlers.dao.ISongSheetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
@Repository
public class SongSheetDao  implements ISongSheetDao {
    @Autowired
    private MongoTemplate mongoTemplate;
}
