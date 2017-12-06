package com.yu.crawlers.dao.repositories;

import com.yu.crawlers.bean.SongSheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface SongSheetRepository extends MongoRepository<SongSheet,String> {
}
