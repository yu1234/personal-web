package com.yu.music.player.config;


import com.yu.spider.music.Main;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by yuliu on 2017/12/7 0007.
 */
@Configuration
public class InitConfig {

    @PostConstruct
    public void init(){
        //初始化音乐爬虫插件
        Main.run();
    }
}
