package com.yu.spider.music.dao.impl;

import com.yu.spider.music.dao.ISongSheetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
@Repository
public class SongSheetDao  implements ISongSheetDao {
    @Autowired
    private MongoTemplate mongoTemplate;
}
