package com.yu.music.player.dao.repositories;

import com.yu.music.player.bean.User;
import com.yu.spider.music.bean.Song;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<UserDetails> findByUsername(String username);
}
