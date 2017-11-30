package com.yu.music.player;

import com.yu.crawlers.implement.SearchType;
import com.yu.crawlers.music163.SpiderMusic;
import com.yu.crawlers.utils.SpringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuliu on 2017/11/30 0030.
 */
public class Test {
    public static void main(String[] args) {
        SpiderMusic spiderMusic = SpringUtil.getBean(SpiderMusic.class);
        Map map = new HashMap();
        map.put("id", "482234719");
        spiderMusic.run(SearchType.PLAY_LIST, map);
    }
}
