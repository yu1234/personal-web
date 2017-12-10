package com.yu.music.player.config;

import com.yu.music.player.handler.MusicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Configuration
public class Routes {
    @Autowired
    private MusicHandler musicHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(
                GET("/api/song/{id}").and(accept(MediaType.APPLICATION_JSON)), musicHandler::getSongById);
    }
}
