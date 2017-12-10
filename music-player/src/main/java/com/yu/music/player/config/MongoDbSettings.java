package com.yu.music.player.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yuliu on 2017/12/11 0011.
 */
@Configuration
public class MongoDbSettings {
    static class OptionsConfig {
        @Bean
        public MongoClientOptions mongoOptions() {
            return MongoClientOptions.builder().socketTimeout(0).connectTimeout(10000).maxWaitTime(120000).build();
        }

    }
}
