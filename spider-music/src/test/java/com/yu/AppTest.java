package com.yu;

import com.yu.crawlers.Main;
import com.yu.crawlers.implement.IParseCallback;
import com.yu.crawlers.netease.SpiderMusic;
import com.yu.crawlers.netease.parse.SongSheetParse;
import com.yu.crawlers.utils.SpringUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class AppTest implements IParseCallback<List<String>> {
    @Test
    public void SpiderMusicTest() {
        SpiderMusic spiderMusic = new SpiderMusic();
    }

    @Test
    public void mainTest() {


    }

    public void successCallback(List<String> list) {

        System.out.println(list.size());
        for (String id : list) {
            System.out.println(id);
        }

    }
}
