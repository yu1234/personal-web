package com.yu.music.player.dao.impl;


import com.yu.spider.music.bean.SongSheet;
import com.yu.music.player.dao.ISongSheetDao;
import org.apache.commons.lang3.ObjectUtils;
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


    /**
     * 是否存在该条记录
     *
     * @param id 主键
     * @return
     */
    @Override
    public boolean isExist(String id) {

        SongSheet songSheet=  mongoTemplate.findById(id, SongSheet.class);
        if(ObjectUtils.allNotNull(songSheet)){
            return true;
        }
        return false;
    }
}
