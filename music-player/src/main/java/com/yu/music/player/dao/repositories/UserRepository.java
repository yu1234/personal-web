package com.yu.music.player.dao.repositories;

import com.yu.music.player.bean.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by yuliu on 2017/11/29 0029.
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
