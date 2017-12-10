package com.yu.music.player.dao.impl;


import com.yu.music.player.dao.ISongDao;
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
