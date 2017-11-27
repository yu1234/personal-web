package com.yu;

import com.yu.crawlers.Main;
import com.yu.crawlers.implement.SearchType;
import com.yu.crawlers.implement.SpiderInfoCallback;
import com.yu.crawlers.music163.SpiderMusic;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class AppTest implements SpiderInfoCallback<List<String>> {
    @Test
    public void SpiderMusicTest() {
        SpiderMusic spiderMusic = new SpiderMusic();
        spiderMusic.run(null, null);
    }

    @Test
    public void mainTest() {
        Main.instance();
        SpiderMusic spiderMusic = Main.getBean(SpiderMusic.class);
        if (ObjectUtils.allNotNull(spiderMusic)) {
            Map map = new HashMap();
            map.put("id", "725376448");
            spiderMusic.run(SearchType.PLAY_LIST, map, this);
        }
    }

    public void successCallback(List<String> list) {

        System.out.println(list.size());
        for (String id : list) {
            System.out.println(id);
        }

    }
}