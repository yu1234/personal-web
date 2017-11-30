package com.yu.crawlers.dao.impl;

import com.yu.crawlers.bean.Song;
import com.yu.crawlers.dao.ISongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
@Repository
public class SongDao implements ISongDao {
    @Autowired
    private MongoTemplate mongoTemplate;
}
